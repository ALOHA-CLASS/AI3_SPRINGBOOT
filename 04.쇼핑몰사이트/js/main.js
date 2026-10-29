/* ==========================================================================
   ALOHA STORE - main.js (수업 실습용)
   ========================================================================== */
document.addEventListener('DOMContentLoaded', function () {

    /* ---------- 1. 아이콘 + 스크롤 애니메이션 ---------- */
    lucide.createIcons();
    AOS.init({ duration: 800, easing: 'ease-out-cubic', once: true, offset: 60 });

    /* ---------- 2. TOP 버튼 ---------- */
    var btnTop = document.querySelector('.btn-top');
    window.addEventListener('scroll', function () {
        btnTop.classList.toggle('on', window.scrollY > 500);
    });

    /* ---------- 3. 모바일 메뉴 ---------- */
    var mobileMenu = document.querySelector('.mobile-menu');
    var dimmed = document.querySelector('.dimmed');

    function toggleMenu(open) {
        mobileMenu.classList.toggle('on', open);
        dimmed.classList.toggle('on', open);
        document.body.style.overflow = open ? 'hidden' : '';
    }

    document.querySelector('.btn-menu').addEventListener('click', function () { toggleMenu(true); });
    document.querySelector('.mobile-menu .btn-close').addEventListener('click', function () { toggleMenu(false); });
    dimmed.addEventListener('click', function () { toggleMenu(false); });
    mobileMenu.querySelectorAll('a').forEach(function (a) {
        a.addEventListener('click', function () { toggleMenu(false); });
    });

    /* ---------- 4. 메인 비주얼 슬라이드 ---------- */
    new Swiper('.visual-swiper', {
        loop: true,
        speed: 900,
        autoplay: { delay: 4500, disableOnInteraction: false },
        pagination: { el: '.visual-pager', clickable: true },
        navigation: { nextEl: '.visual-next', prevEl: '.visual-prev' }
    });

    /* ---------- 5. 타임세일 상품 슬라이드 ---------- */
    new Swiper('.sale-swiper', {
        slidesPerView: 2,
        spaceBetween: 16,
        loop: true,
        navigation: { nextEl: '.sale-next', prevEl: '.sale-prev' },
        breakpoints: {
            768: { slidesPerView: 3, spaceBetween: 24 },
            1280: { slidesPerView: 4, spaceBetween: 30 }
        }
    });

    /* ---------- 6. 리뷰 슬라이드 ---------- */
    new Swiper('.review-swiper', {
        slidesPerView: 1,
        spaceBetween: 24,
        autoplay: { delay: 4000, disableOnInteraction: false },
        pagination: { el: '.review-pager', clickable: true },
        breakpoints: {
            768: { slidesPerView: 2 },
            1280: { slidesPerView: 3 }
        }
    });

    /* ---------- 7. 타임세일 카운트다운 (오늘 자정까지) ---------- */
    var hh = document.getElementById('hh');
    var mm = document.getElementById('mm');
    var ss = document.getElementById('ss');

    function pad(n) { return String(n).padStart(2, '0'); }

    function updateClock() {
        var now = new Date();
        var end = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 1); // 다음 날 00:00
        var diff = Math.floor((end - now) / 1000);

        hh.textContent = pad(Math.floor(diff / 3600));
        mm.textContent = pad(Math.floor((diff % 3600) / 60));
        ss.textContent = pad(diff % 60);
    }
    updateClock();
    setInterval(updateClock, 1000);

    /* ---------- 8. 장바구니 / 찜 버튼 (예시 동작) ---------- */
    var cartCount = document.querySelector('.util .cart .count');

    document.querySelectorAll('.prd-card .hover button').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var isCart = btn.querySelector('[data-lucide="shopping-cart"]');
            var name = btn.closest('.prd-card').querySelector('.name').textContent;

            if (isCart) {
                cartCount.textContent = parseInt(cartCount.textContent, 10) + 1;
                alert('[' + name + ']을(를) 장바구니에 담았습니다. (실습용 예제)');
            } else {
                alert('[' + name + ']을(를) 찜 목록에 추가했습니다. (실습용 예제)');
            }
        });
    });

    /* ---------- 9. 부드러운 앵커 이동 ---------- */
    document.querySelectorAll('a[href^="#"]').forEach(function (a) {
        a.addEventListener('click', function (e) {
            var target = document.querySelector(a.getAttribute('href'));
            if (!target) return;
            e.preventDefault();
            window.scrollTo({
                top: target.getBoundingClientRect().top + window.scrollY - 90,
                behavior: 'smooth'
            });
        });
    });

});
