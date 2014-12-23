# Random Tweet Bot

## 概要

- 指定 Twitter ユーザのツイートから閾値以上の fav 数のツイートを収集
- その中からランダムにツイート

## 必要環境

- Java (>= 1.8)
- Maven

## 前準備

Twitter アカウントの consumer key, access token を取得する。以下の通りプロパティファイルを作成する。

### `twitter4j.properties`

以下のように設定。

    $ debug=false
    $ oauth.consumerKey=<consumer key>
    $ oauth.consumerSecret=<consumer secret>
    $ oauth.accessToken=<access token>
    $ oauth.accessTokenSecret=<access token secret>

プロジェクトのルートディレクトリに保存。

### Bot 用のプロパティファイル

以下のように設定。

    $ screenName=<ユーザ ID>
    $ filePath=<収集したツイートデータ保存パス>
    $ favCount=<収集するツイートの fav 閾値>
    $ intervalMinutes=<ツイートの時間間隔（分）>

保存場所は任意。

## ビルド

    $ mvn clean package dependency:copy-dependencies -DincludeScope=runtime

## 実行

    $ java -jar random-tweet-bot-<version>.jar <プロパティファイルのパス>

## ライセンス

- [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
