# FindFriend

MadCamp Week2 1분반 FindFriend팀

- 사용자가 원하는 방을 만들거나 다른 사람의 방에 가입할 수 있습니다.
- 나의 방 탭에서 다른 사용자들과 실시간 채팅이 가능합니다.

### a. 개발 팀원

- 김수환 - KAIST 전산학부(수리과학과) 22학번
- 이하늘 - 숙명여자대학교 문헌정보학과(컴퓨터과학과) 22학번

### b. 개발환경

- Language: Python, Kotlin
- Front-end: Android Studio
- Back-end: Flask
- DataBase: MySQL
- OS: Android

- IDE: Visual Studio code
- Target Device: Android 

### c. DB 구조
![image](https://github.com/suhwankim03/madcamp_week2/assets/155048947/41b6f995-c6bd-4c01-9ce4-c34bdd74657f)


### d. 어플리케이션 소개

## Page 0 - Login/SignUp

<img src="https://github.com/suhwankim03/madcamp_week2/assets/155048947/f45345c7-7860-4b66-9ca4-2d8f28d8f73a" width="180"/> 
<img src="https://github.com/suhwankim03/madcamp_week2/assets/155048947/acecd51e-6534-4b74-a806-506ded61b57e" width="180"/> 

***Major features***

- 앱에서 직접 회원가입을 할 수 있고, 아이디, 비밀번호, 닉네임의 글자수 제한이 있습니다.
- 카카오톡으로도 회원가입을 할 수 있습니다.

***부가설명***

- (프론트)
- 카카오톡으로 로그인 하거나, 직접 회원가입을 하면 users 테이블에 회원 정보가 저장됩니다.
- 회원가입 시 이미 존재하는 아이디가 DB에 있으면 회원가입할 수 없습니다.
- 로그인 시 회원정보를 DB에서 확인해서 회원 정보가 users에 존재할 때만 로그인 할 수 있습니다.

## Page 1 - My Room

<img src="https://github.com/suhwankim03/madcamp_week2/assets/155048947/0880cdf4-ed11-4f93-bc21-73fb036e9fea" width="180"/>
<img src="https://github.com/suhwankim03/madcamp_week2/assets/155048947/0fe085e9-289f-4bce-af5a-d309488441cc" width="180"/>
<img src="https://github.com/suhwankim03/madcamp_week2/assets/155048947/587df35c-1af1-4263-958f-2e8f43ad9c94" width="180"/>

***Major features***

- 나의 파티 탭은 내가 현재 가입된 방이 화면에 나타납니다.
- 방을 짧게 누르면 실시간 채팅 탭으로 넘어갑니다.
- 방을 길게 누르면 방을 나갈 수 있습니다.
- 스크롤바를 내려 새로고침이 가능합니다.

***부가설명***

- (프론트)
- myroom과 room을 join하여 사용자가 가입한 방의 정보들을 추출한다.
- MySQL은 실시간 업데이트 기능이 없기에 웹소켓 통신을 사용하여 실시간 채팅을 구현했다.
- 실시간 채팅의 채팅 내역들을 저장하기 위해 chat 테이블에 저장한다.
- 방 정보에는 보안을 위해 사용자의 닉네임만 표시된다.
- 방장이 방을 삭제하면 room 테이블에서도 삭제된다.

## Page 2 - Find Room & Add Room

<img src="https://github.com/suhwankim03/madcamp_week2/assets/155048947/9c8f188b-0cd1-493d-8b3e-cdc2467c580a" width="180"/>

***Major features***

- 전체 방 탭은 모든 방의 정보들을 보여주는 탭입니다.
- 방을 클릭했을 때 방의 정보가 뜨고, 해당 방에 가입할 수 있습니다.
- 플러스 버튼을 누르면 방을 추가할 수 있고, 날짜 선택은 달력에서 선택합니다.
- 스크롤바를 내려 새로고침이 가능합니다.
    

***부가설명***

- (프론트)
- room 테이블에서 방의 정보들을 가져와서 화면에 표시한다.
- 방을 만들면 만든 사람의 닉네임이 표시된다.
- 방의 ID는 auto increment 기능을 사용하여 자동으로 증가하고, 방이 추가되면 방 ID가 정렬된다.
- 방 ID는 사용자에게 보이지는 않는다(DB 관리용).

## Page 3 - Profile

<img src="https://github.com/pbc1017/trippy/assets/63749140/b29b0ca7-80a6-46ce-b8bf-b66b544f7733" width="180"/>
<img src="https://github.com/pbc1017/trippy/assets/63749140/9b724b18-7627-46a0-87f5-2e1f6c283c29" width="180"/>
<img src="https://github.com/pbc1017/trippy/assets/63749140/a043ca1a-64d2-4315-9901-72e5e78f7207" width="180"/>

***Major features***

- 사용자의 아이디와 닉네임이 화면에 출력됩니다.
- 카카오로 로그인 시 카카오톡에서 사용하던 프로필 사진이 출력됩니다.
- 로그아웃 버튼을 누르면 다시 로그인 화면으로 돌아갑니다.

***기술설명***

- (프론트)

