# 지하철 실시간 도착정보
## contents
[1. 개요](#1-개요)  
[2. 개발 환경](#2-개발-환경)  
[3. 개발 일정](#3-개발-일정)  
[4. 기능과 실행 화면](#4-기능과-실행-화면)  
[5. 패키지 구조도](#5-패키지-구조도)  


## 1. 개요
### 1.1. 선정이유
지하철을 자주 이용하는 사람들을 위해 만들었습니다.

## 2. 개발 환경
- 운영체제 : Windows10
- Front-end : html, CSS, Bootstrap
- Back-end : JDK 11, Eclipse EE 2021-09
- Database : MySQL
- Server : Apache Tomcat 9.0
- Version Control : Git
- API : [서울시 지하철 실시간 도착정보](https://data.seoul.go.kr/dataList/OA-12764/F/1/datasetView.do)
- 화면 크기 : .container-sm( 540px )

## 3. 개발 일정
- 기간 : 2022.01.19 ~ 2022.01.22  
- History

|날짜|내용|
|----|----|
|2022.01.19|주제 및 API 선정, 테스트|
|2022.01.20|Controller 초안 작성|
|2022.01.21 ~ 2022.01.22|기능 구현 및 디버깅|
|2022.01.23|프론트 제작|

## 4. 기능과 실행 화면
### 1. 역 검색
     - select로 검색하기
### 2. 운행 정보 표시
     - 상행선, 하행선 각각 도착 예정 시간
### 3. 현재 시간 (실시간)
### 4. 실행 화면
![subway](https://user-images.githubusercontent.com/88884623/155671346-086f6cf1-d77a-489a-8944-c5032862cb0c.gif)

## 5. 패키지 구조도
     

  ![image](https://user-images.githubusercontent.com/88884623/155670411-e3840721-ecf5-49e8-9d20-622785509d4d.png)
  




