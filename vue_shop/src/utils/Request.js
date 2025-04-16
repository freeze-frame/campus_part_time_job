import axios from 'axios';
// 导入nprogress包和样式,用于进度条显示
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';
import { Message } from 'element-ui';
import router from '@/router';
router.push({});

// 重新创建一个带根节点的axios对象
const request = axios.create({
	baseURL: 'http://127.0.0.1:8888/api/v1',
	withCredentials: true,
});

// 拦截器（英文：Interceptors）会在每次发起 ajax 请求和得到响应的时候自动被触发
// 每次API操作都要拦截器带token令牌
request.interceptors.request.use((config) => {
	// 在request拦截器中，展示进度条NProgress.start()
	NProgress.start();
	config.headers.Authorization = sessionStorage.getItem('token');
	return config;
});

request.interceptors.response.use((response) => {
	if (response.data.code == '50000') {
		console.log('sdasdsd');
		Message.error(response.data.msg);
		localStorage.removeItem('token');
		// 重新跳转到login页面
		router.push('/login');
	}
	// 在response拦截器中，隐藏进度条NProgress.done()
	NProgress.done();

	return response;
});
export default request;
