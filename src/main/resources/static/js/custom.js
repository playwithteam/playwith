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
        $('td .check-type-1 > input[type="checkbox"]:checked').each(function () {
            selectedIds.push($(this).val());
        });

        // 선택된 항목이 있는지 확인
        if (selectedIds.length > 0) {
            if (confirm('선택한 항목을 정말 삭제하시겠습니까?')) {

                var jsonData = JSON.stringify({ "selectedIds": selectedIds });

                $.ajax({
                    type: 'POST',
                    url: '/qna/deleteSelectedQna',
                    contentType: 'application/json',
                    data: jsonData,
                    success: function (response) {
                        console.log(response);
                        location.reload();
                    },
                    error: function (error) {
                        console.error(error);
                    }
                });
            }
            else return false;
        } else {
            alert('삭제할 항목을 선택하세요.');
        }
    });

    //체크박스 전제 선택
    $("#cbx_chkAll").click(function() {
        if($("#cbx_chkAll").is(":checked")) $("input[name=chk]").prop("checked", true);
        else $("input[name=chk]").prop("checked", false);
    });

    $("input[name=chk]").click(function() {
        var total = $("input[name=chk]").length;
        var checked = $("input[name=chk]:checked").length;

        if(total != checked) $("#cbx_chkAll").prop("checked", false);
        else $("#cbx_chkAll").prop("checked", true);
    });

    $("#customTimeInput").on("change", function() {
        // 분을 00분으로 고정하고 시간과 분을 합치기
        var fixedMinutes = "00";
        var selectedTime = $(this).val() || "00:00";
        var modifiedTime = selectedTime.split(":")[0] + ":" + fixedMinutes;

        // 수정된 값을 다시 input 요소에 설정
        $(this).val(modifiedTime);
      });

      $("#areaSelect").change(function () {
          var selectedArea = $(this).val();

          // Ajax 호출을 통해 서버에서 해당 지역의 구장 리스트를 가져옴
          $.ajax({
              type: "GET",
              url: "/stadium/stadiumList", // 여기에 실제 백엔드 API 엔드포인트를 넣어주세요
              data: { area: selectedArea },
              success: function (stadiums) {
                  // 가져온 데이터를 바탕으로 두 번째 select 박스 업데이트
                  var stadiumSelect = $("#stadiumSelect");
                  stadiumSelect.empty(); // 기존 옵션 제거

                  stadiumSelect.append('<option value="">구장</option>'); // 기본 옵션 추가

                  $.each(stadiums, function (index, stadium) {
                      stadiumSelect.append('<option value="' + stadium.id + '">' + stadium.name + '</option>');
                  });
              },
              error: function (error) {
                  console.log("Error fetching stadiums: " + error);
              }
          });
      });

      // gameDate input 요소에 change 이벤트 리스너 등록
      $("#gameDate").on("change", function () {
          // 현재 날짜 가져오기
          var currentDate = new Date();

          // 선택된 날짜 가져오기
          var selectedDate = new Date($(this).val());

          // 선택된 날짜가 오늘 날짜보다 이전인 경우 경고 메시지 표시
          if (selectedDate <= currentDate) {
                $(this).val("");
              alert("최소 1일 전 매칭만 등록 가능합니다.");
          }
      });

      $('button#favor_btn').click(function(event) {
          event.preventDefault();
          $(this).toggleClass("active");
      });

        //매칭 상세에서 주소 복사 버튼 클릭시
        $("#copyButton").on("click", function() {
          var address = $("#addressDisplay").text();
          copyToClipboard(address);
          alert("구장 주소가 복사되었습니다.");
        });
        function copyToClipboard(text) {
          var textarea = $("<textarea>")
              .val(text)
              .appendTo("body")
              .select();
          document.execCommand("copy");
          textarea.remove();
        }

});