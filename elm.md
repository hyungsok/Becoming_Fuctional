# Elm

\( 참고링크 : [https://www.gitbook.com/book/evancz/an-introduction-to-elm/details](https://www.gitbook.com/book/evancz/an-introduction-to-elm/details) \)



**Elm이란?**

* 자바스크립로 컴파일되는 함수형언어 
* React와 같은 프로젝트와 경쟁하여 웹사이트 및 웹앱을 만들때 사용함.
* 단순성\(simplicity\), 사용편의성\(ease-of-use\), quality tooling??? 매우 강조함 
* 문법이 Haskell를 비슷함



install : [http://install.elm-lang.org/Elm-Platform-0.18.pkg](http://install.elm-lang.org/Elm-Platform-0.18.pkg)

![](/assets/스크린샷 2017-02-22 오후 6.25.52.png)

\( 위와같이 맥용 install 설치파일하면 간단하게 설치가 완료 \)

설치후 사용 설치된 툴

* elm-repl
* elm-reactor
* elm-make
* elm-package



Hello World 

```elm
import Html exposing (text)

main =
  text "Hello, World!"
```











