// ajax
async function $ajax( obj ) {
    console.log('data:', obj.data);
    console.log('url:', obj.url);
    console.log('type:', obj.type);
    
    try {
        // 💎 CSRF TOKEN
        const csrfToken = $('meta[name="_csrf"]').attr('content');
        const csrfHeader = $('meta[name="_csrf_header"]').attr('content');

        let data
        if(obj.type == 'GET' || obj.type == 'DELETE') { data = obj.data; }
        else { data = obj.data instanceof FormData ? obj.data : JSON.stringify(obj.data)}
        let response = await $.ajax({
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            url: obj.url,
            type: obj.type,
            data: data,
            contentType: obj.data instanceof FormData ? false : 'application/json; charset=utf-8',
            processData: obj.data instanceof FormData ? false : true,                                    // 데이터를 쿼리 문자열로 변환하지 않음
            // dataType: 'json'              // 서버의 응답 데이터 타입 (단순 SUCCESS, FAIL 문자열일 경우 생략)
        });

        if (obj.success) obj.success(response);
        return response;
    } catch (jqXHR) {
        console.log('[ajax.js] error -------------------------');

        // 서버가 내려준 에러 응답 본문을 그대로 반환하여 호출부에서 사용 가능하게 함
        const response = jqXHR.responseJSON !== undefined ? jqXHR.responseJSON : jqXHR.responseText;

        if (obj.error) obj.error(response);
        return response;
    }   

}