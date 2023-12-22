$(document).ready(function(){
    
    $("").click(function(){
        $("").addClass("active");
        $("").removeClass("active");
        $("").toggleClass("active");
        $("").siblings().next().nextAll().nextUntil().prev().prevAll().prevUntil();
        $("").parent().parents();
        $("").css("", "");
    });

    //  텝메뉴
    function tabMenu(no) {
        $(".tab-cnt-box-" + no + " > li:not("+$(".tab-menu-box-" + no + " > li > a.active").attr("href")+")").hide();
        $(".tab-menu-box-" + no + " > li > a").click(function(){ 
            $(".tab-menu-box-" + no + " > li > a").removeClass("active"); 			
            $(this).addClass("active");	
            $(".tab-cnt-box-" + no + " > li").hide(); 
            $($(this).attr("href")).show(); 
            return false;       
        }); 
    }
    
    for (let tabMenuNum = 1; tabMenuNum <= 20; tabMenuNum++) {
        tabMenu(tabMenuNum);
    }

    // 자주 묻는 질문
    $(".question-list-box > li .area-1").click(function() {
        $(this).parent("li").toggleClass("active");
        $(this).siblings(".area-2").stop().slideToggle(300);
    });

    $(".top-btn-box").click( function() {
        $("html, body").animate( { scrollTop : 0 }, 400 );
        return false;
    });

    $(".scroll-btn").click(function (event) {
        event.preventDefault();
        $('html,body').animate({ scrollTop: $(this.hash).offset().top }, 500);
    });

    $(window).scroll(function () {

        let scroll = $(window).scrollTop();

        if (scroll > 0) {
            $(".top-btn-box").addClass("active");
        }
        else{
            $(".top-btn-box").removeClass("active");
        }

        let area0 = $("#area_0").offset().top - 80,
            area1 = $("#area_1").offset().top - 80,
            area2 = $("#area_2").offset().top - 80,
            area3 = $("#area_3").offset().top - 80

        if ($(this).scrollTop() >= area0) {
            $(".header-area .menu-box li a").removeClass("active");
            $(".header-area .menu-box li:nth-child(1) a").addClass("active");
        }
        if ($(this).scrollTop() >= area1) {
            $(".header-area .menu-box li a").removeClass("active");
            $(".header-area .menu-box li:nth-child(2) a").addClass("active");
        }
        if ($(this).scrollTop() >= area2) {
            $(".header-area .menu-box li a").removeClass("active");
            $(".header-area .menu-box li:nth-child(3) a").addClass("active");
        }
        if ($(this).scrollTop() >= area3) {
            $(".header-area .menu-box li a").removeClass("active");
            $(".header-area .menu-box li:nth-child(5) a").addClass("active");
        }

    });


    // "선택 삭제" 버튼에 대한 클릭 이벤트
    $('#selectDelBtn').click(function () {
        // 선택된 체크박스 값들을 저장할 배열
        var selectedIds = [];

        // 각 선택된 체크박스의 값을 배열에 추가
        $('.check-type-1 > input[type="checkbox"]:checked').each(function () {
            selectedIds.push($(this).val());
        });

        console.log(selectedIds);
        console.log(selectedIds.length);


        // 선택된 항목이 있는지 확인
        if (selectedIds.length > 0) {
        var jsonData = JSON.stringify({ "selectedIds": selectedIds });
            // AJAX를 사용하여 선택된 값을 서버로 전송
            $.ajax({
                type: 'POST',
                url: '/qna/deleteSelectedQna', // 실제 엔드포인트로 변경
                contentType: 'application/json',
                data: jsonData,
                success: function (response) {
                    // 서버로부터의 성공 응답을 처리
                    console.log(response);
                },
                error: function (error) {
                    // 서버로부터의 오류 응답을 처리
                    console.error(error);
                }
            });
        } else {
            alert('삭제할 항목을 최소한 하나 이상 선택하세요.');
        }
    });

});