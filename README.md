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
- Server: Flask
- DataBase: MySQL
- OS: Android

- IDE: Visual Studio code
- Target Device: Android 

### c. DB 구조
![image](https://github.com/suhwankim03/madcamp_week2/assets/155048947/41b6f995-c6bd-4c01-9ce4-c34bdd74657f)


### d. 어플리케이션 소개

## Page 0 - Start/Login/SignUp

<img src="https://github.com/suhwankim03/madcamp_week2/assets/145983374/15f0cfd7-2279-44e4-b26b-686e0b691d2d" width="180"/> 
<img src="https://github.com/suhwankim03/madcamp_week2/assets/155048947/f45345c7-7860-4b66-9ca4-2d8f28d8f73a" width="180"/> 
<img src="https://github.com/suhwankim03/madcamp_week2/assets/155048947/acecd51e-6534-4b74-a806-506ded61b57e" width="180"/> 

***Major features***

- 앱에서 직접 회원가입을 할 수 있고, 아이디, 비밀번호, 닉네임의 글자수 제한이 있습니다.
- 카카오톡으로도 회원가입, 로그인을 할 수 있습니다.

***부가설명***

- Fade-in 애니메이션이 있는 스플래시 화면 로딩 구현하였습니다.
- SharedPreferences 를 사용하여 앱을 설치한뒤 최초 1회 로그인 혹은 회원가입 시 앱을 껐다 켜도 로그인이 유지됩니다.
- 카카오톡으로 로그인 하거나, 직접 회원가입을 하면 users 테이블에 회원 정보가 저장됩니다.
- 회원가입 시 이미 존재하는 아이디가 DB에 있으면 회원가입할 수 없습니다.
- 로그인 시 회원정보를 DB에서 확인해서 회원 정보가 users에 존재할 때만 로그인 할 수 있습니다.

## Page 1 - My Room

<img src="https://github.com/suhwankim03/madcamp_week2/assets/155048947/0880cdf4-ed11-4f93-bc21-73fb036e9fea" width="180"/>
<img src="https://github.com/suhwankim03/madcamp_week2/assets/155048947/587df35c-1af1-4263-958f-2e8f43ad9c94" width="180"/>
<img src="https://github.com/suhwankim03/madcamp_week2/assets/145983374/054752b4-619f-4e03-971e-7d8bb80218ac" width="180"/>
<img src="https://github.com/suhwankim03/madcamp_week2/assets/145983374/0c5cf3ec-ab45-4b8c-9b5e-07c72a3da037" width="180"/>

***Major features***

- 나의 파티 탭은 내가 현재 가입된 방이 화면에 나타납니다.
- 화면을 아래로 스와이프하여 새로고침이 가능합니다.
- 방을 길게 누르면 방을 나갈 수 있습니다.
- 방을 짧게 누르면 실시간 채팅 탭으로 넘어갑니다.
- 채팅 탭에서 오른쪽 상단에 상세 버튼을 누르면 해당 방의 정보를 확인할 수 있습니다.

***부가설명***

- Recyclerview를 사용하여 화면에 파티를 나타내고, 스크롤이 가능합니다.
- swiperefreshlayout 을 사용하여 스와이프 시 새로고침이 가능합니다.
- setOnClickListener 를 사용하여 채팅방 이동을, setOnLongClickListener 를 사용하여 파티 탈퇴 dialog를 띄웁니다.
- 방장이 방을 나가면 방이 폐쇄되어 room 테이블에서도 삭제합니다.
- myroom과 room을 join하여 사용자가 가입한 방의 정보들을 추출합니다.
  
- Thread 기능을 이용하여 채팅을 화면에 표시합니다.
- Scrollview 를 사용해 채팅이 여러 개일경우 스크롤해서 확인할 수 있습니다.
- GestureDetector를 이용해 채팅 화면에서 화면을 더블탭 하면 스크롤뷰의 최하단으로 이동하도록 했습니다. 
- MySQL은 실시간 업데이트 기능이 없기에 웹소켓 통신을 사용하여 실시간 채팅을 구현했습니다.
- 실시간 채팅의 채팅 내역들을 저장하기 위해 chat 테이블에 저장합니다.
- 파티 정보에는 보안을 위해 사용자의 닉네임만 표시합니다.

## Page 2 - Find Room & Add Room
<img src="https://github.com/suhwankim03/madcamp_week2/assets/145983374/aa7df9f4-6a80-4ed7-bf26-c7ae88cb5587" width="180"/>
<img src="https://github.com/suhwankim03/madcamp_week2/assets/145983374/6808be9d-9c13-47e0-8751-ac60d9689c73" width="180"/>
<img src="https://github.com/suhwankim03/madcamp_week2/assets/145983374/28e33e4c-2c88-4b68-aa3e-9068527ea2d9" width="180"/>
<img src="https://github.com/suhwankim03/madcamp_week2/assets/145983374/9828e02b-2305-404e-bfd2-6b1e77390b15" width="180"/>

***Major features***

- 전체 파티 탭은 모든 파티의 정보들을 보여주는 탭입니다.
- 원하는 파티를 클릭했을 때 방의 정보가 뜨고, 체크 표시를 눌러 해당 방에 가입할 수 있습니다.
- 플러스 버튼을 누르면 방을 추가할 수 있습니다.
- 스크롤바를 내려 새로고침이 가능합니다.
    

***부가설명***

- room 테이블에서 방의 정보들을 가져와서 화면에 표시합니다.
- Recyclerview를 사용하여 화면에 파티를 나타내고, 스크롤이 가능합니다.
- swiperefreshlayout 을 사용하여 스와이프 시 새로고침이 가능합니다.
- setOnClickListener 를 통해 파티의 상세 정보가 있는 JoinRoomActivity로 이동하고, 가입이 가능합니다.

- 우측 상단 플러스 버튼을 누르면 AddRoomActivity로 이동하여 새로운 방을 만들 수 있습니다.
- 다른 내용들은 키패드로 직접 입력할 수 있으며, 일정은 DatePickerDialog와 TimePickerDialog를 사용해서 입력 가능합니다.
- 방의 ID는 auto increment 기능을 사용하여 자동으로 증가하고, 방이 추가되면 방 ID를 기준으로 정렬됩니다.
- 방 ID는 사용자에게 보이지는 않습니다(DB 관리용)

## Page 3 - Profile
<img src="https://github.com/suhwankim03/madcamp_week2/assets/145983374/ceb32d8f-3ede-4fc4-a1df-00b664c040c2" width="180"/>
<img src="https://github.com/suhwankim03/madcamp_week2/assets/145983374/4a50bbbb-02bc-4231-936a-c76b30139508" width="180"/>

***Major features***

- 사용자의 아이디와 닉네임이 화면에 출력됩니다.
- 카카오로 로그인 시 카카오톡에서 사용하던 프로필 사진이 출력됩니다.
- 로그아웃 버튼을 누르면 다시 로그인 화면으로 돌아갑니다.

***부가설명***

- SharedPreferences 를 통해 내부 저장소 데이터를 삭제해 로그아웃 시 로그인 유지가 해제됩니다.

