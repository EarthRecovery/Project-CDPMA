import request from '@/utils/request'

// 登录方法
export function login(operatorName, operatorPhone, operatorEmail, operatorPassword) {
  return request({
    url: '/auth/login',
    headers: {
      isToken: false,
      repeatSubmit: false
    },
    method: 'post',
    data: {
      operator_name: operatorName,
      operator_phone: operatorPhone,
      operator_email: operatorEmail,
      operator_password: operatorPassword
    }
  })
}

export function getInfo(){
  return request({
    url: '/system-user/operator/getInfo',
    method: 'get'
  })
}
