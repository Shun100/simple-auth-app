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

- コンパイラ設定
  - デフォルトではIDEの内臓コンパイラを使用するので、Gradleのコンパイラを使用するよう変更する
    - `settings` -> `Build, Execution, Deployment` -> `Gradle` -> `Build and run using`をGradleに変更

## ビルド方法

- `./gradlew bootRun`
  - キャッシュを使用しない場合は`./gradlew clean bootRun`
  - ビルド後`localhost:8080`にアクセスすると`localhost:8080/login`にリダイレクトしてSpring Securityデフォルトのログインページが表示される

## Memo

### Spring Securityのコンポーネント

- `SecurityConfig`: アクセス権限の設定、ログイン・ログアウト処理の設定を記述する
- `UserDetails`: ユーザ名, パスワード, 権限などユーザに関する設定情報を記述する
- `UserDetailService`: `UserDetails`を取得するメソッドを定義する

### 認可の設定対象と設定方法

- 設定対象
  - ページ (HTML)
  - メソッド
  - リソース (URL)

- 設定方法
  - ページに対して設定する方法

  ```HTML
    <!-- ライブラリ追加 -->
    <html xmlns:sec="http://www.thymeleaf.org/extras/spring-security">

    <!-- タグに対して権限設定 -->
    <li sec:authorize="hasAuthority('ADMIN')"><a th:href="@{/users}">ユーザ一覧</a></li>
  ```

  - メソッドに対して設定する方法

  ```Java
    // メソッドに対して権限設定
    /* UserService.java */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserEntity> findAll() {
      return userRepository.findAll();
    }

    // 権限設定有効化
    /* MethodSecurityConfig.java */
    @Configuration
    @EnableMethodSecurity(prePostEnabled = true)
      public class MethodSecurityConfig {
    }
  ```

  - リソースに対して設定する方法

  ```Java
    // SecurityFilterに設定を追加する
    /* SecurityConfig.java */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
        // 認可ルールの設定
        .authorizeHttpRequests(auth -> auth
          .requestMatchers("/login/**").permitAll()
          .requestMatchers("/users/**").hasAuthority("ADMIN") //  権限設定
          .anyRequest().authenticated()
        )
    }
  ```
