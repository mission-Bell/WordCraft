package com.hyomoto.wordcraft.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import com.hyomoto.wordcraft.model.WordCraftModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WordCraftService {

    public WordCraftModel replace(WordCraftModel wordCraftModel) {


        try (FileInputStream fs = new FileInputStream(wordCraftModel.getInputPath())) {

            try (// Wordドキュメント取得
                    XWPFDocument wordDocument = new XWPFDocument(fs)) {
                // パラグラフListを取得
                List<XWPFParagraph> paragraphs = wordDocument.getParagraphs();

                int count = 0;
                // パラグラフごとに処理実施
                for (XWPFParagraph paragraph : paragraphs) {

                    // 共通のプロパティを持つ文字の塊。行や一文でないことに注意。
                    List<XWPFRun> xwpfRuns = paragraph.getRuns();

                    // 1Runごとに処理実行
                    for (XWPFRun xwpfRun : xwpfRuns) {

                        // nullのおそれがあるため事前チェック
                        String runText = xwpfRun.getText(0);
                        log.info(runText);
                        // nullではなく、かつターゲット文字列を含む場合
                        // 複数パターン（イコール条件や数値の以上・以下など）にする場合、case文にしつつ別メソッドで実装
                        if (Objects.nonNull(runText)
                                && runText.matches(wordCraftModel.getTargetIncluding())) {

                            // ログ出力用
                            String beforParagraphText = paragraph.getText();

                            // 置換処理
                            xwpfRun.setText(runText.replace(wordCraftModel.getTarget(),
                                    wordCraftModel.getReplacement()), 0);

                            String message = String.format("%s -> %s", beforParagraphText,
                                    paragraph.getText());
                            // ログ出力。何から何に置換したか？
                            log.info(message);
                            // 返却メッセージ設定
                            wordCraftModel.addMessages(message);

                            count++;

                        }
                    }

                } ;
                // 何ヶ所変換したかメッセージ設定
                wordCraftModel.addMessages(String.format("変換した箇所数：%s", count));
                wordCraftModel.addMessages("Success!");

                // ファイルの書き込み処理
                wordDocument.write(new FileOutputStream(wordCraftModel.getOutPath()));
            }

        } catch (Exception e) {
            log.error(null, e);
            wordCraftModel.addMessages(e.getMessage());
        }

        return wordCraftModel;

    }

}
