/**
 * 문자열의 마지막(끝) 문자의 종성 포함 여부 확인
 * @param value - Target String
 * @returns 종성 포함 여부
 */
function hasCoda(value) {
	return ((value.charCodeAt(value.length - 1) - 0xAC00) % 28) > 0;
}


/**
 * 필드(Elemenet) 유효성 검사
 * @param target - 검사 대상 Element
 * @param fieldName - 필드명
 * @param focusTarget - 포커스 대상 Element
 * @returns 필드 입력(선택) 여부
 */
function isValid(target, fieldName, focusTarget) {
	if (target.value.trim()) {
		return true;
	}

	const particle = (hasCoda(fieldName)) ? '을' : '를'; // 조사
	const elementType = (target.type === 'text' || target.type === 'password' || target.type === 'search' || target.type === 'textarea') ? '입력' : '선택';
	alert(`${fieldName + particle} ${elementType}해 주세요.`);

	target.value = '';
	(!focusTarget ? target : focusTarget).focus();
	throw new Error(`"${target.id}" is required...`)
}


/**
 * 데이터 조회
 * @param uri - API Request URI
 * @param params - Parameters
 * @returns json - 결과 데이터
 */
function getJson(uri, params) {

	let json = {}

	$.ajax({
		url: uri,
		type: 'get',
		dataType: 'json',
		data: params,
		async: false,
		success: function(response) {
			json = response;
		},
		error: function(request, status, error) {
			console.log(error)
		}
	})

	return json;
}


/**
 * 데이터 저장/수정/삭제
 * @param uri - API Request URI
 * @param method - API Request Method
 * @param params - Parameters
 * @returns json - 결과 데이터
 */
function callApi(uri, method, params) {

	let json = {}

	$.ajax({
		url: uri,
		type: method,
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		data: (params) ? JSON.stringify(params) : {},
		async: false,
		success: function(response) {
			json = response;
		},
		error: function(request, status, error) {
			console.log("request :: ", request);
			console.log("status :: ", status);
			console.log("error :: ", error);
		}
	})

	return json;
}

/**
 * Promise 패턴이 적용
 * method = ajax methods: POST, GET, PUT, DELETE, etc. Default is GET.
 * returnType = html, json, text, etc.
 */
const loaderContainer = document.querySelector('.loader-container');

var _api = {
	call: function(url, obj, method, returnType, beforesend, success, failure) {
		
		
		obj = obj === null || undefined ? {} : obj;
		$.ajax({
			method: method || 'GET',
			data: !$.isEmptyObject(obj) ? JSON.stringify(obj) : null,
			contentType: function() {
				switch (returnType) {
					case 'json':
						return 'application/json';
					case 'text':
						return 'text/plain';
					case 'buffer':
						return 'arraybuffer';
					case 'html':
					default:
						return 'text/html';
				}
			}(returnType === 'json' ? 'application/json; charset=utf-8' : ''),
			url: url,
			dataType: returnType,
			beforeSend: function(xhr, obj) {
				if (beforesend) {
					beforesend(xhr, obj);
				}
				
				_api.showLoader();
			}
		}).done(function(result, textStatus, xhr) {
			if (success) success(result)
		}).fail(function(result, textStatus, xhr) {
			if (failure) failure(result)
		}).always(function(result, textStatus, xhr) {
			// Implement code here that you want to run whenever the call is complete regardless of success or failure.
			_api.hideLoader();
		});

	},
	showLoader:function() {
		//console.log("showLoader");
		loaderContainer.style.display = 'block';
	},
	hideLoader:function() {
		//console.log("hideLoader");
		loaderContainer.style.display = 'none';
	}
}
/* loading icon 을 안보이게 한다. */
window.addEventListener('load', () => {
	// loaderContainer.style.display = 'none';
	_api.hideLoader();
});