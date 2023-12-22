// 스와이퍼 옵션
var swiper = new Swiper(".swiper-100", {
    loop: true,
    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    },
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
    slidesPerView: 3, 
    spaceBetween: 30, 
    slidesPerGroup: 3, 
    effect: "fade", 
    direction: "vertical", 
    autoplay : {
        delay : 5000,
        disableOnInteraction : false,
        stopOnLastSlide : true
    },
    centeredSlides: true,
    speed : 4000,
    breakpoints: {
        360: {
            slidesPerView: 1,  
            spaceBetween: 0,
        },
        767: {
            slidesPerView: 2,  
            spaceBetween: 40,
        },
        1023: {
            slidesPerView: 3,
            spaceBetween: 50,
        },
    },
});

// 스와이퍼-1
var swiper = new Swiper(".swiper-1", {
    loop: true,
    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    },
    slidesPerView: 1,
    spaceBetween: 0,
    autoplay : {
        delay : 4000,
        disableOnInteraction : false,
        stopOnLastSlide : true
    },
});

// 스와이퍼-2
var swiper = new Swiper(".swiper-2", {
    slidesPerView: "auto",
    spaceBetween: 12,
    navigation: {
        nextEl: ".swiper-button-next-2",
        prevEl: ".swiper-button-prev-2",
    },
});

// 스와이퍼-3
var swiper = new Swiper(".swiper-3", {
    slidesPerView: "auto",
    spaceBetween: 12,
    navigation: {
        nextEl: ".swiper-button-next-3",
        prevEl: ".swiper-button-prev-3",
    },
});