import Vue from 'vue';
import App from './App.vue';
import router from './router';
import './plugins/element.js';
import '@/assets/css/global.css';
import axios from 'axios';
import Nav from '@/view/Nav/Nav.vue';

// 1.导入全局vue-table-with-tree-grid插件
import TreeTable from 'vue-table-with-tree-grid';
// 2.导入全局vue-quill-editor 富文本编辑器 插件
import VueQuillEditor from 'vue-quill-editor';

// 2.导入富文本编辑器 vue-quill-editor的样式
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
import { Loading, Empty, Drawer, Descriptions, DescriptionsItem, Carousel, CarouselItem } from 'element-ui';

Vue.use(Loading.directive);
Vue.use(Empty);
Vue.use(Drawer);
Vue.use(Descriptions);
Vue.use(DescriptionsItem);
Vue.component(Carousel.name, Carousel);
Vue.component(CarouselItem.name, CarouselItem);

import Router from 'vue-router';

const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
	return originalPush.call(this, location).catch((err) => err);
};

// 3.echarts导入
import * as echarts from 'echarts';

// 3.echarts挂载
Vue.prototype.$echarts = echarts;

// 2.注册插件
Vue.use(VueQuillEditor);

Vue.config.productionTip = false;
Vue.prototype.axios = axios;
// 1.注册全局成组件
Vue.component('tree-table', TreeTable);
Vue.component('my-nav', Nav);

// 定义全局时间戳转标准的过滤器
Vue.filter('dateFormt', function (originVal) {
	const dt = new Date(originVal);
	const y = dt.getFullYear();
	const m = (dt.getMonth() + 1 + '').padStart(2, '0');
	const d = (dt.getDate() + '').padStart(2, '0');

	const hh = (dt.getHours() + '').padStart(2, '0');
	const mm = (dt.getMinutes() + '').padStart(2, '0');
	const ss = (dt.getSeconds() + '').padStart(2, '0');
	return `${y}-${m}-${d} ${hh}:${mm}:${ss}`;
});

new Vue({
	router,
	render: (h) => h(App),
}).$mount('#app');
