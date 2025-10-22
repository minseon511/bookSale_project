## 中古書籍取引プラットフォーム (Book Market)
<img width="2880" height="1612" alt="image" src="https://github.com/user-attachments/assets/792b00f8-c9e9-4ba3-a64f-3744527bcfad" />
![중고책방_전체구조_최종](https://github.com/user-attachments/assets/1ddcc988-5dd3-4982-ab05-0dade0aa3980)
<img width="1656" height="1196" alt="booksaledb" src="https://github.com/user-attachments/assets/7beee410-65e8-4653-9ffe-c1f4f112aaff" />

---

## プロジェクト紹介

Spring BootとJDBCを基盤に開発した**中古書籍オンライン取引ウェブサイト**です。ユーザーは直接書籍を登録し、他のユーザーの書籍を検索、確認、カートに入れるなどの相互作用が可能です。ウェブ開発能力の向上および実務機能の実装を目標に制作しました。

---

## 主な機能

* 書籍リスト照会: カードレイアウト及びページネーション適用
* 書籍登録/修正/削除: 本人作成の投稿に対するCRUD機能（画像アップロード含む）
* 詳細情報: 書籍の状態（書き込み、破損の有無など）表示
* 検索: タイトル、著者、出版社キーワード検索
* カート: 商品追加、選択購入/削除機能
* ログイン: Google OAuth2ソーシャルログイン
* 権限管理: ログイン状態及び作成者本人確認後の機能アクセス制御

---

## 使用技術

* Backend: Java, Spring Boot, Spring Security (OAuth2), JDBC
* Frontend: JSP, HTML5, CSS3, JavaScript, Bootstrap 5
* Database: MySQL
* Server: Apache Tomcat (内蔵)
* Build Tool: Maven
