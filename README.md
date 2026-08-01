# simple-auth-app

認証・認可学習用のシンプルなアプリ (React + Spring Boot)

## Dependencies

| 名称                 | 用途                   | Maven Coordinates                                                                                                     |
| -------------------- | ---------------------- | --------------------------------------------------------------------------------------------------------------------- |
| Spring Web           | Rest API・MVC          | `org.springframework.boot:spring-boot-starter-web`                                                                    |
| Spring Security      | 認証・認可             | `org.springframework.boot:spring-boot-starter-security`                                                               |
| PostgreSQL Driver    | PostgreSQL接続         | `org.postgresql:postgresql`                                                                                           |
| Spring Data JDBC     | DAOの実装の簡略化      | `org.springframework.boot:spring-boot-starter-data-jdbc`                                                              |
| Lombok               | 各種便利アノテーション | `org.projectlombok:lombok`                                                                                            |
| Validation           | 入力チェック           | `org.springframework.boot:spring-boot-starter-validation`                                                             |
| Tymeleaf             | SSR                    | `org.springframework.boot:spring-boot-starter-thymeleaf` <br> `org.thymeleaf.extras:thymeleaf-extras-springsecurity6` |
| Spring Boot DevTools | 自動リロード           | `org.springframework.boot:spring-boot-devtools`                                                                       |
| Test                 | テスト                 | `org.springframework.boot:spring-boot-starter-test` <br> `org.springframework.security:spring-security-test`          |

## プロジェクトを作成したらまずやること

- JDBCの設定
  - `application.properties`に設定を記述

  ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/simple-auth-app
    spring.datasource.username=postgres
    spring.datasource.password=password
  ```

- Hot Reloadの設定
  - `guild.gradle`に設定を記述

  ```gradle
    bootRun {
      sourceResources sourceSets.main
    }
  ```

## ビルド方法

- `./gradlew bootRun`
  - キャッシュを使用しない場合は`./gradlew clean bootRun`
  - ビルド後`localhost:8080`にアクセスすると`localhost:8080/login`にリダイレクトしてSpring Securityデフォルトのログインページが表示される
