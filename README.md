# 名言 Bot [![Build Status](https://travis-ci.org/kymmt90/meigen-bot.svg?branch=master)](https://travis-ci.org/kymmt90/meigen-bot)

## 概要

- 指定 Twitter ユーザのツイートから閾値以上のお気に入り数のツイートを収集
- その中からランダムにツイート

## 必要環境

- Java (>= 1.8)
- Maven

## 前準備

まず、Twitter アカウントの consumer key, access token を取得しておく。

Bot を動作させるために、以下のプロパティファイルを作成する。

### Twitter4J 用プロパティファイル

内部で利用しているライブラリ [Twitter4J](http://twitter4j.org/ja/index.html) のために、`twitter4j.properties` という名前のプロパティファイルを作成する。設定項目は以下の通り。

    debug=false
    oauth.consumerKey=<consumer key>
    oauth.consumerSecret=<consumer secret>
    oauth.accessToken=<access token>
    oauth.accessTokenSecret=<access token secret>

`twitter4j.properties` は Bot 実行時のカレントディレクトリに保存する。

### Bot 用プロパティファイル

Bot の動作設定用のプロパティファイルを作成する。ファイル名は任意。設定項目は以下の通り。

    screenName=<ツイートを収集するユーザのスクリーンネーム（カンマ区切りで複数指定可）>
    favCount=<各ユーザから収集するツイートのお気に入り数閾値（カンマ区切りで複数指定可）>
    filePath=<収集した JSON 形式ツイートデータの保存パス>
    intervalMinutes=<Bot のツイート時間間隔（分）>
	reply=<リプライをツイートするなら true, そうでないなら false>

`screenName` と `favCount` の中の要素は対応しているため、設定する要素数は同じにする。例えば、以下のように設定する。

    screenName=user1,user2
	favCount=2,3
	filePath=path/to/file.json
	intervalMinutes=60
	reply=false

この場合、`user1` のお気に入り数 `2` 以上のツイートと、`user2` のお気に入り数 `3` 以上のツイートを収集する。

## ビルド

    $ mvn clean package dependency:copy-dependencies -DincludeScope=runtime

## 実行

    $ java -jar meigen-bot-<version>.jar <Bot 用プロパティファイルのパス>

## ライセンス

- [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
