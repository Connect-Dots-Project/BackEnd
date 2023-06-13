<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>마커 생성하기</title>
    <style>
        .overlaybox {
            position: relative;
            width: 200px;
            padding: 10px;
            background-color: #ffffff;
            border: 1px solid #dddddd;
            border-radius: 4px;
            box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.3);
        }

        .boxtitle {
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .hotplaceImg img {
            width: 100%;
            height: auto;
        }

        .hotplaceInfo {
            margin-top: 10px;
            line-height: 1.5;
        }
    </style>
</head>
<body>
<div id="map" style="width:100%;height:350px;"></div>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=08637e590784f47dfde7ec9b2a40910a&libraries=services"></script>
<script>
    var mapContainer = document.getElementById('map'); // 지도를 표시할 div
    var mapOption = {
        center: new kakao.maps.LatLng(37.4996237314472, 127.03051594993698), // 지도의 중심좌표 (학원: 중앙정보기술인재개발원)
        level: 3 // 지도의 확대 레벨
    };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    <c:forEach var="hotplace" items="${hotplaceList}">
    var latitude = "${hotplace.hotplaceLatitude}";
    var longitude = "${hotplace.hotplaceLongitude}";
    var markerPosition = new kakao.maps.LatLng(parseFloat(latitude), parseFloat(longitude));
    var marker = new kakao.maps.Marker({position: markerPosition});
    // marker.setMap(map);
    // 마커 이미지를 생성합니다
    <%--var markerImage = new kakao.maps.MarkerImage("${hotplace.hotplaceImg}", new kakao.maps.Size(30, 30));--%>

    // // 마커를 생성하고 지도에 표시합니다
    // var marker = new kakao.maps.Marker({
    //     position: markerPosition,
    //     image: markerImage
    // });
    marker.setMap(map);

    // 커스텀 오버레이에 표시할 내용입니다
    var content = '<div class="overlaybox">' +
        '    <div class="boxtitle">${hotplace.hotplaceName}</div>' +
        '    <div class="hotplaceImg"><img src="${hotplace.hotplaceImg}" alt="사진"></div>' +
        '    <div class="hotplaceInfo">' +
        '        <p>${hotplace.hotplaceFullAddress}</p>' +
        '    </div>' +
        '</div>';

    // 커스텀 오버레이가 표시될 위치입니다
    var overlayPosition = markerPosition;

    // 커스텀 오버레이를 생성하고 지도에 표시합니다
    var customOverlay = new kakao.maps.CustomOverlay({
        position: overlayPosition,
        content: content,
        xAnchor: 0.5,
        yAnchor: 1.2
    });
    customOverlay.setMap(map);
    </c:forEach>
</script>
</body>
</html>