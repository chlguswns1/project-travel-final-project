<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>TRIP_USERINFO</title>
<link rel="icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/resources/assets/favicon.ico" />
<!-- Font Awesome icons (free version)-->
<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
	crossorigin="anonymous"></script>
<!-- Google fonts-->
<link
	href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic"
	rel="stylesheet" type="text/css" />
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800"
	rel="stylesheet" type="text/css" />
<!-- Core theme CSS (includes Bootstrap)-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/styles.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/bootstrap1.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min1.css">

<style>
.navbar {
	position: fixed;
	top: 0;
	width: 100%;
	z-index: 9999;
}

@font-face {
    font-family: 'OTWelcomeRA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2110@1.0/OTWelcomeRA.woff2') format('woff2');
    font-weight: 700;
    font-style: normal;
}

@font-face {
    font-family: 'OTWelcomeRA1';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2110@1.0/OTWelcomeRA.woff2') format('woff2');
    font-weight: 400;
    font-style: normal;
}

@font-face { /* 귀여운 느낌의 글씨체 */
    font-family: 'omyu_pretty';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2304-01@1.0/omyu_pretty.woff2') format('woff2');
    font-weight: normal;
    font-style: normal;
}

@font-face { /* 정형화된 느낌이면서 단정 */
    font-family: 'KBIZHanmaumGothic';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/KBIZHanmaumGothic.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'SeoulNamsanM';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/SeoulNamsanM.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

.b {
	font-family: 'SeoulNamsanM';
}

.a {
	font-family: 'SeoulNamsanM';
	font-size: 17px;
	color: #3a3a3a;
}  
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="memberInfo.me">TRIP</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarColor03"
				aria-controls="navbarColor03" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarColor03">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><span class="nav-link active">${user.MEMBER_ID }님</span>
					</li>
					
					<c:if test="${ id == 'admin' }">
						<li class="nav-item"><a class="nav-link"
						href="getTravelSpotList.do">TravelList</a></li>
					</c:if>
					<c:if test="${ id != 'admin' }">
						<li class="nav-item"><a class="nav-link"
						href="getTravelSpotListuser.do">TravelList</a></li>
					</c:if>
					
					<c:if test="${ id != 'admin' }">	
					<li class="nav-item"><a class="nav-link"
						href="bookmarklist.do?user_seq=${user.MEMBER_SEQ }">MyList</a></li>
					</c:if>	
					<!-- <li class="nav-item"><a class="nav-link"
						href="memberdetail.me">MyInfo</a></li> -->
					<li class="nav-item"><a class="nav-link" href="logout.me">Logout</a>
					</li>

				</ul>
				<form class="d-flex" action="getTravelSpotListuser.do" method="get">
					<select name="searchCondition" class="b">
						<c:forEach items="${ conditionMap }" var="option">
							<option value="${ option.value }">${ option.key }
						</c:forEach>
					</select> <input class="form-control me-sm-2" type="text"
						placeholder="Search" name="searchKeyword"> <input
						class="btn btn-secondary my-2 my-sm-0" type="submit"
						value="Search">
				</form>
			</div>
		</div>
	</nav>
	<!-- Page Header-->
	<header class="masthead" style="background-color: #B8E7E1;">
		<div class="container position-relative px-4 px-lg-5">
			<div class="row gx-4 gx-lg-5 justify-content-center">
				<div class="col-md-10 col-lg-8 col-xl-7">
					<div class="page-heading">
						<h1>MY INFORM</h1>
					</div>
				</div>
			</div>
		</div>
	</header>

	<!-- Main Content-->
	<main class="mb-4">
		<div class="container px-4 px-lg-5">
			<div class="row gx-4 gx-lg-5 justify-content-center">
				<div class="col-md-10 col-lg-8 col-xl-7">
					<table width="600px" cellspacing="0" cellpadding="0" align="center">
						<tr>
							<td colspan=2>
								<!--회원 정보 확인하기 -->
								<div class="card border-secondary mb-3"
									style="max-width: 50rem;">
									<div class="card-body">
										<p class="card-text">
										<form name="joinform" id="joinform" action="memberupdate.me"
											onsubmit="return check()">
											<p align="center">
											<table width="100%" height="80%">
												<tr class="b">
													<td width="40%" align="center"><font>이름</font>
													</td>

													<td>&nbsp;&nbsp;&nbsp; <input name="MEMBER_NAME"
														type="hidden" value="${user.MEMBER_NAME }" />
														${user.MEMBER_NAME}
													</td>
												</tr>
												<tr>
													<td align="center"><font class="b">아이디</font></td>

													<td>&nbsp;&nbsp;&nbsp; <input name="MEMBER_ID"
														type="hidden" value="${user.MEMBER_ID }" />
														${user.MEMBER_ID }
													</td>
												</tr>
												<input name="MEMBER_PW" type="hidden"
													value="${user.MEMBER_PW }" />
												<%-- 
												<tr>
													<td align="center">
													<font size="2">비밀번호</font>
													</td>
													
													<td>&nbsp;&nbsp;&nbsp; 
													<input name="MEMBER_PW" type="hidden" value="${user.MEMBER_PW }" />
													${user.MEMBER_PW}
													</td>
												</tr> 
												--%>
												<tr>
													<td align="center"><font class="b">이메일 주소</font></td>

													<td>&nbsp;&nbsp;&nbsp; <input name="MEMBER_EMAIL"
														type="hidden" value="${user.MEMBER_EMAIL }" />
														${user.MEMBER_EMAIL}@ <input name="MEMBER_EMAIL_GET"
														type="hidden" value="${user.MEMBER_EMAIL_GET }" />
														${user.MEMBER_EMAIL_GET}
													</td>
												</tr>

												<tr>
													<td align="center"><font class="b">전화번호</font></td>

													<td>&nbsp;&nbsp;&nbsp; <input name="MEMBER_PHONE"
														type="hidden" value="${user.MEMBER_PHONE }" />
														${user.MEMBER_PHONE}
													</td>
												</tr>
											</table>

											<table width="100%">
												<br />
												<br />
												<tr align="center">
													<td><a href="memberupdate.me"> <!-- "memberupdate.me" -->
															<button type="button" class="btn btn-secondary b">회원 정보 관리</button>
													</a></td>
												</tr>
											</table>
										</form>
										</p>
									</div>
								</div>



							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</main>
	<!-- Footer-->
	<footer class="border-top">
		<div class="container px-4 px-lg-5">
			<div class="row gx-4 gx-lg-5 justify-content-center">
				<div class="col-md-10 col-lg-8 col-xl-7">
					<ul class="list-inline text-center">
						<li class="list-inline-item"><a href="#!"> <span
								class="fa-stack fa-lg"> <i
									class="fas fa-circle fa-stack-2x"></i> <i
									class="fab fa-twitter fa-stack-1x fa-inverse"></i>
							</span>
						</a></li>
						<li class="list-inline-item"><a href="#!"> <span
								class="fa-stack fa-lg"> <i
									class="fas fa-circle fa-stack-2x"></i> <i
									class="fab fa-facebook-f fa-stack-1x fa-inverse"></i>
							</span>
						</a></li>
						<li class="list-inline-item"><a href="#!"> <span
								class="fa-stack fa-lg"> <i
									class="fas fa-circle fa-stack-2x"></i> <i
									class="fab fa-github fa-stack-1x fa-inverse"></i>
							</span>
						</a></li>
					</ul>
					<div class="small text-center text-muted fst-italic">Copyright
						&copy; Your Website 2023</div>
				</div>
			</div>
		</div>
	</footer>
	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script
		src="${pageContext.request.contextPath}/resources/js/scripts.js"></script>
</body>
</html>
