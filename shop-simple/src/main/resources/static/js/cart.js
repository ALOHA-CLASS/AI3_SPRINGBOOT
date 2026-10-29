/**
 * 장바구니 담기 - 비동기 fetch(POST)로 처리 (수량변경/삭제는 cart/list.html 참고)
 */
(function () {

    // CSRF 토큰 헤더 구성 (main_layout.html의 meta 태그 참고)
    function csrfHeaders() {
        const tokenMeta = document.querySelector('meta[name="_csrf"]');
        const headerMeta = document.querySelector('meta[name="_csrf_header"]');
        const headers = { 'Content-Type': 'application/json' };
        if (tokenMeta && headerMeta) {
            headers[headerMeta.content] = tokenMeta.content;
        }
        return headers;
    }

    function updateCartBadge(count) {
        const badge = document.querySelector('.util .cart .count');
        if (badge) badge.textContent = count;
    }

    // 응답이 실패(4xx/5xx)면 서버가 내려준 message로 reject
    function handleResponse(res) {
        if (res.ok) return res.json();
        return res.json()
            .catch(function () { return {}; })
            .then(function (data) { throw new Error(data.message || '요청 처리 중 오류가 발생했습니다.'); });
    }

    // ========== 장바구니 담기 (POST /cart, JSON) ==========
    document.querySelectorAll('form.js-add-form').forEach(function (form) {
        form.addEventListener('submit', function (e) {
            // "바로 주문하기"(formmethod=get) 버튼 클릭 시에는 기존 방식대로 이동
            if (e.submitter && e.submitter.getAttribute('formmethod') === 'get') {
                return;
            }
            e.preventDefault();

            const productNo = Number(form.querySelector('[name="productNo"]').value);
            const quantity = Number(form.querySelector('[name="quantity"]').value) || 1;

            fetch('/cart', {
                method: 'POST',
                headers: csrfHeaders(),
                body: JSON.stringify({ productNo: productNo, quantity: quantity })
            })
                .then(handleResponse)
                .then(function (data) {
                    updateCartBadge(data.cartCount);
                    alert('장바구니에 담았습니다.');
                })
                .catch(function (err) { alert(err.message); });
        });
    });

})();
