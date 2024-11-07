<h1 style='background-color: rgba(55, 55, 55, 0.4); text-align: center'>Roomly API 설계(명세)서</h1>

해당 API 명세서는 'Roomly ERP - Roomly'의 REST API를 명세하고 있습니다.  

- Domain : <http://localhost:4000>  

***
  
<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Auth(호스트) 모듈</h2>

Roomly 서비스의 인증 및 인가와 관련된 REST API 모듈입니다.  
로그인, 회원가입, 사업자 정보 확인 등의 API가 포함되어 있습니다.  
Auth 모듈은 인증 없이 요청할 수 있습니다.  
  
- url : /api/roomly/auth/host  

***
<h2 style= 'text-align: center'> 호스트 </h2>

#### 로그인  
  
##### 설명

클라이언트는 사용자 아이디와 평문의 비밀번호를 입력하여 요청하고 아이디와 비밀번호가 일치한다면 인증에 사용될 token과 해당 token의 만료 기간을 응답 데이터로 전달 받습니다. 만약 아이디 혹은 비밀번호가 하나라도 틀린다면 로그인 정보 불일치에 해당하는 응답을 받게됩니다. 네트워크 에러, 서버 에러, 데이터베이스 에러, 토큰 생성 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/sign-in**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| hostId | String | 호스트의 아이디 | O |
| hostPw | String | 호스트의 비밀번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/roomly/auth/host/sign-in" \
 -d "hostId=qwer1234" \
 -d "hostPw=P!ssw0rd"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| accessToken | String | Bearer token 인증 방식에 사용될 JWT | O |
| expiration | Integer | JWT 만료 기간 (초단위) | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "accessToken": "${ACCESS_TOKEN}",
  "expiration": 32400
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (로그인 정보 불일치)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "SF",
  "message": "Sign in failed."
}
```

**응답 실패 (토큰 생성 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "TCF",
  "message": "Token creation failed."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 아이디 중복 확인  
  
##### 설명

클라이언트는 사용할 아이디를 입력하여 요청하고 중복되지 않는 아이디라면 성공 응답을 받습니다. 만약 아이디가 중복된다면 아이디 중복에 해당하는 응답을 받게됩니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/id-check**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| hostId | String | 중복확인 할 사용자의 아이디 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/roomly/host/auth/id-check" \
 -d "userId=qwer1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (중복된 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DI",
  "message": "Duplicated user id."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 전화번호 인증  
  
##### 설명

클라이언트는 숫자로만 이루어진 11자리 전화번호를 입력하여 요청하고 이미 사용중인 전화번호인지 확인 후 4자리의 인증번호를 해당 전화번호에 문자를 전송합니다. 인증번호가 정상적으로 전송이 된다면 성공 응답을 받습니다. 만약 중복된 전화번호를 입력한다면 중복된 전화번호에 해당하는 응답을 받게됩니다. 네트워크 에러, 서버 에러, 데이터베이스 에러, 문자 전송 실패가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/tel-auth**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| hostTelNumber | String | 인증 번호를 전송할 사용자의 전화번호 (11자리 숫자) | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/roomly/auth/host/tel-auth" \
 -d "hostTelNumber=01011112222"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (중복된 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DT",
  "message": "Duplicated tel number."
}
```

**응답 실패 (인증번호 전송 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "TF",
  "message": "Auth number send failed."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 인증번호 확인  
  
##### 설명

클라이언트는 사용자 전화번호와 인증번호를 입력하여 요청하고 해당하는 전화번호와 인증번호가 서로 일치하는지 확인합니다. 일치한다면 성공에 대한 응답을 받습니다. 만약 일치하지 않는 다면 전화번호 인증 실패에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/tel-auth-check**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| telNumber | String | 인증 번호를 확인할 사용자 전화번호 | O |
| authNumber | String | 인증 확인에 사용할 인증 번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/roomly/auth/host/tel-auth-check" \
 -d "telNumber=01011112222" \
 -d "authNumber=1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (전화번호 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "TAF",
  "message": "Tel number authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 사업자 번호 중복확인
  
##### 설명

클라이언트는 사용자(호스트) 사업자 번호를 요청한다. 일치한다면 성공에 대한 응답을 받습니다. 만약 중복한 번호가 존재한다면 중복된 사업자 번호에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/business-number-check**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| b_no | String | 사업자 등록 번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/roomly/auth/host/business-number-check" \
 -d "b_no = 0000000000" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (중복된 사업자번호)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "DBN",
  "message": "Duplicated business number."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***

#### - 사업자 번호 전송
  
##### 설명

클라이언트는 사용자(호스트) 사업자 정보 및 번호 요청하고 해당하는 공공데이터센터로 데이터를 전송한다. 일치한다면 성공에 대한 응답을 받습니다. 만약 일치하지 않는 다면 사업자 인증 실패에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/validate-business**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| b_no | String | 사업자 등록 번호 | O |
| start_dt | String | 개업일자(YYYYMMDD) | O |
| p_nm | String | 대표자 성명 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/validate-business" \
 -d "b_no = 0000000000" \
 -d "start_dt = 20240101" \
 -d "p_nm = 홍길동" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| b_no | String | 사업자등록번호 | O |
| valid | String | 진위확인 결과 코드 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (사업자번호 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NB",
  "message": "No exist business number."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***

#### - 사업자인증 이미지 중복확인
  
##### 설명

클라이언트는 사용자(호스트) 사업자 정보 인증 이미지를 요청한다. 일치한다면 성공에 대한 응답을 받습니다. 만약 중복된 파일이 존재한다면 중복된 파일에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/business-image**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| businessImage | String | 사업자정보 인증 이미지 | O |


###### Example

```bash
curl -v -X POST "http://localhost:4000/api/roomly/auth/host/business-image" \
 -d "businessImage = test.png" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (중복된 사업자정보 인증 이미지)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "DIM",
  "message": "Duplicated image. "
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***

#### - 회원가입(호스트)  
  
##### 설명

클라이언트는 사용자(호스트)의 이름, 아이디, 비밀번호, 전화번호, 인증번호, 사업자 정보, 사업자 정보 인증 이미지를 입력하여 요청하고 회원가입이 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 만약 존재하는 아이디일 경우 중복된 아이디에 대한 응답을 받고, 만약 존재하는 전화번호일 경우 중복된 전화번호에 대한 응답을 받고, 전화번호와 인증번호가 일치하지 않으면 전화번호 인증 실패에 대한 응답을 받고, 이미 등록된 사업자 번호일 경우 중복된 사업자 번호에 대한 응답을 받고, 존재하지 않는 사업자 번호일 경우 존재하지 않는 사업자번호에 대한 응답을 받고, 만약 존재하는 사업자 정보 인증 이미지일 경우 중복된 이미지에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/sign-up**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| hostId | String | 호스트의 아이디 | O |
| hostPw | String | 호스트의 비밀번호 (8~13자의 영문 + 숫자) | O |
| histName | String | 호스트의 이름 | O |
| hostTelNumber | String | 사용자의 전화번호 (11자의 숫자) | O |
| authNumber | String | 전화번호 인증번호 | O |
| hostBusinessNumber | String | 사업자 등록 번호 | O |
| businessName | String | 대표자 성명 | O |
| businessStartDay | String | 개업일자(YYYYMMDD) | O |
| businessImage | String | 사업자정보인증이미지 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/roomly/auth/sign-up" \
 -d "hostName=홍길동"\
 -d "hostId=qwer1234"\
 -d "hostPw=qwer1234"\
 -d "hostTelNumber=01011112222"\
 -d "hostBusinessNumber = 0000000000" \
 -d "businessName = 홍길동" \
 -d "businessStartDay = 20240101" \
 -d "businessImage = test.png" \

```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (중복된 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DI",
  "message": "Duplicated user id."
}
```

**응답 : 실패 (중복된 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DT",
  "message": "Duplicated user tel number."
}
```

**응답 : 실패 (전화번호 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "TAF",
  "message": "Tel number authentication failed."
}
```
**응답 실패 (사업자번호 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NB",
  "message": "No exist business number."
}
```

**응답 실패 (중복된 사업자정보 인증 이미지)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "DIM",
  "message": "Duplicated image. "
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Accommodation 모듈</h2>

Roomly 서비스의 숙소와 관련된 REST API 모듈입니다.  
호스트계정을 관리자가 승인하에 숙소 등록 및 수정 삭제 등의 API가 포함되어 있습니다.
Accommodation 모듈은 모두 인증이 필요합니다.
  
- url : /api/roomly/accommodation  

***

#### - 숙소 승인 요청 및 등록
  
##### 설명
클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하고 숙소 등록에 필요한 이미지, 숙소 이름, 숙소 종류, 숙소 설명, 카테고리 를 입력하여 요청하고 
성공적으로 요청했을 시 성공에 대한 응답을 받습니다. 
만약  중복된 이미지일 경우 중복된 이미지에 대한 응답을 받고, 
만약 중복된 숙소이름일 경우 중복된 숙소이름에 대한 응답을 받고, 
만약 숙소 종류가 없을 경우에는 숙소 종류가 존재하지 않는것에 대한 응답을 받고, 
만약 숙소 설명이 존재하지 않는 경우 숙소 설명이 존재하지 않는 것에 대한 응답을 받고,
만약 카테고리가 존재하지 않는 경우 카테고리가 없는 것에 대한 응답을 받습니다.
네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- end point : **/register**

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| accommodationName | String | 숙박 이름 | O |
| accommodationMainImage | String | 대표 이미지 | O |
| accommodationAddress | String | 대표 이미지 | O |
| accommodationType | String | 숙소 유형 | O |
| accommodationIntroduce | String | 숙소 소개 | O |
| categoryArea | String | 지역 카테고리 | O |
| categoryPet | boolean | 펫 카테고리 | X |
| categoryNonSmokingArea | boolean | 흡연 카테고리 | X |
| categoryIndoorSpa | boolean |  스파 카테고리 | X |
| categoryDinnerParty | boolean | 석식 카테고리 | X |
| categoryWifi | boolean | 와이파이 카테고리 | X |
| categoryCarPark | boolean | 주차장 카테고리 | X |
| categoryPool | boolean | 수영장 카테고리 | X |
| hostId | String | 호스트의 아이디 | O |


###### Example

```bash
curl -v -X POST "http://localhost:4000/api/roomly/auth/sign-up" \
 -d "accommodationName= C조 호텔"\
 -d "accommodationMainImage=https://asdhfjaksdf.png"\
 -d "accommodationAddress=부산시 부산진구 ~"\
 -d "accommodationIntroduce=어서오세요~"\
 -d "accommodationType= 호텔"\
 -d "categoryArea = 부산" \
 -d "categoryPet = true" \
 -d "categoryNonSmokingArea = true" \
 -d "categoryIndoorSpa = true" \
 -d "categoryDinnerParty = true" \
 -d "categoryWifi = true" \
 -d "categoryCarPark = true" \
 -d "categoryPool = true" \
 -d "hostId = qwer1234" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (중복된 숙소 이름)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DAN",
  "message": "Duplicated accommodation name."
}
```

**응답 실패 (승인되지 않은 계정)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 숙소 서브 이미지 등록
  
##### 설명
클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하고
숙소 등록에 필요한 서브 이미지에 대한 요청을 하고,
성공적으로 요청했을 시 성공에 대한 응답을 받습니다. 
만약  중복된 이미지일 경우 중복된 이미지에 대한 응답을 받고, 
만약 중복된 숙소 이미지일 경우 중복된 숙소이미지에 대한 응답을 받고, 
만약 숙소 이름이 없을 경우에는 숙소가 존재하지 않는것에 대한 응답을 받습니다.
네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- end point : **/image**

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| accommodationName | String | 숙박 이름 | O |
| accommodationImage | String | 숙소 서브 이미지 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/roomly/auth/sign-up" \
 -d "accommodationName= C조 호텔"\
 -d "accommodationImage=https://asdhfjaksdf.png"\
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (존재하지 않는 숙소)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NA",
  "message": "No exist accommodation. "
}
```

**응답 실패 (승인되지 않은 계정)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 숙소 이용정보 등록
  
##### 설명
클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하고 
숙소 등록에 필요한 서브 이미지에 대한 요청을 하고,
성공적으로 요청했을 시 성공에 대한 응답을 받습니다. 
만약  중복된 이미지일 경우 중복된 이미지에 대한 응답을 받고, 
만약 중복된 숙소 이미지일 경우 중복된 숙소이미지에 대한 응답을 받고, 
만약 숙소 이름이 없을 경우에는 숙소가 존재하지 않는것에 대한 응답을 받습니다.
네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.

- method : **POST**  
- end point : **/information**

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| accommodationName | String | 숙박 이름 | O |
| title | String | 숙소이용 정보 제목 | O |
| context | String | 내용 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/roomly/auth/sign-up" \
 -d "accommodationName= C조 호텔"\
 -d "title=OTT"\
 -d "context=넷플릭스, 디즈니+ 이용가능"\
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (존재하지 않는 숙소)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NA",
  "message": "No exist accommodation. "
}
```

**응답 실패 (승인되지 않은 계정)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 숙소 정보(숙소, 숙소 서브이미지, 숙소 이용정보, 객실, 객실서브 이미지) 가져오기
  
##### 설명
클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하고 URL에 숙소 이름을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다.
네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.

- method : **GET**  
- end point : **/register**

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -v -X get "http://localhost:4000/api/roomly/accommodation/{accommodationName}" 
```
##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| accommodationName | String | 숙박 이름 | O |
| accommodationMainImage | String | 대표 이미지 | O |
| accSubImages | String[] | 대표 이미지 | O |
| accommodationAddress | String | 대표 이미지 | O |
| accommodationType | String | 숙소 유형 | O |
| accommodationIntroduce | String | 숙소 소개 | O |
| categoryArea | String | 지역 카테고리 | O |
| categoryPet | boolean | 펫 카테고리 | X |
| categoryNonSmokingArea | boolean | 흡연 카테고리 | X |
| categoryIndoorSpa | boolean |  스파 카테고리 | X |
| categoryDinnerParty | boolean | 석식 카테고리 | X |
| categoryWifi | boolean | 와이파이 카테고리 | X |
| categoryCarPark | boolean | 주차장 카테고리 | X |
| categoryPool | boolean | 수영장 카테고리 | X |
| hostId | String | 호스트의 아이디 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (중복된 숙소 이름)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DAN",
  "message": "Duplicated accommodation name."
}
```

**응답 실패 (승인되지 않은 계정)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 숙소 정보 수정
  
##### 설명
클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하고 URL에 숙소 이름을 포함하고 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다.
네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.

- method : **PATCH**  
- end point : **//update/{accommodationName}**

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -v -X get "http://localhost:4000/api/roomly/accommodation/{accommodationName}" 
```
##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| accommodationName | String | 숙박 이름 | O |
| accommodationMainImage | String | 대표 이미지 | O |
| accSubImages | String[] | 대표 이미지 | O |
| accommodationAddress | String | 대표 이미지 | O |
| accommodationType | String | 숙소 유형 | O |
| accommodationIntroduce | String | 숙소 소개 | O |
| categoryArea | String | 지역 카테고리 | O |
| categoryPet | boolean | 펫 카테고리 | X |
| categoryNonSmokingArea | boolean | 흡연 카테고리 | X |
| categoryIndoorSpa | boolean |  스파 카테고리 | X |
| categoryDinnerParty | boolean | 석식 카테고리 | X |
| categoryWifi | boolean | 와이파이 카테고리 | X |
| categoryCarPark | boolean | 주차장 카테고리 | X |
| categoryPool | boolean | 수영장 카테고리 | X |
| hostId | String | 호스트의 아이디 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (중복된 숙소 이름)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DAN",
  "message": "Duplicated accommodation name."
}
```

**응답 실패 (승인되지 않은 계정)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***