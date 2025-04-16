import Vue from 'vue';
import VueRouter from 'vue-router';

// 懒加载方式导入组件
const Login = () => import(/* webpackChunkName:"login_home_wecome" */ '@/components/Login');
const Home = () => import(/* webpackChunkName:"login_home_wecome" */ '@/components/Home');
const Wecome = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/Wecome');
const StuManage = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/root/StuManage');
const StuNotes = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/root/StuNotes');
const StuHome = () => import(/* webpackChunkName:"login_home_wecome" */ '@/components/StuHome');
const ManageHome = () => import(/* webpackChunkName:"login_home_wecome" */ '@/components/ManageHome');
const StuIndex = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/StuIndex');
const ManageIndex = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/ManageIndex');
const MyShop = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/shop/MyShop');
const ShopChat = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/shop/ShopChat');
const ShopManage = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/shop/ShopManage');
const NotesManage = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/shop/NotesManage');
const OneRecruitment = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/shop/Recruitment');
const MerchantsManage = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/root/MerchantsManage');
const EvaluateManage = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/root/EvaluateManage');
const RootShopManage = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/root/ShopManage');
const StuIdentity = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/root/StuIdentity');
const MerIdentity = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/root/MerIdentity');
const ShopIdentity = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/root/ShopIdentity');
const StuRecShow = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/student/StuRecShow');
const StuMySelfInfo = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/student/StuMySelfInfo');
const StuMySelfNotes = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/student/StuMySelfNotes');
const StuDeliver = () => import(/* webpackChunkName:"login_home_wecome" */ '@/view/student/StuDeliver');
Vue.use(VueRouter);

const routes = [
	{ path: '/', redirect: '/login' },
	{ path: '/login', component: Login },
	{
		path: '/home',
		component: Home,
		children: [
			{ path: '/home', redirect: '/home/wecome' },
			{ path: 'wecome', component: Wecome },
			{ path: '/stu/show', component: StuManage },
			{ path: '/stu/notes', component: StuNotes },
			{ path: '/stu/stuIdentity', component: StuIdentity },
			{ path: '/business/show', component: MerchantsManage },
			{ path: '/business/merchantIdentity', component: MerIdentity },
			{ path: '/shop/shopIdentity', component: ShopIdentity },
			{ path: '/shop/show', component: RootShopManage },
			{ path: '/evaluate/show', component: EvaluateManage },
		],
	},
	{
		path: '/suthome',
		component: StuHome,
		children: [
			{ path: '/suthome', redirect: '/suthome/stuindex' },
			{ path: '/suthome/stuindex', component: StuIndex },
			{ path: '/suthome/chat', name: 'Im', component: () => import('@/components/Im') },
			{ path: '/suthome/myself/info', component: StuMySelfInfo },
			{ path: '/suthome/myself/notes', component: StuMySelfNotes },
			{ path: '/suthome/deliver', component: StuDeliver },
			{ path: '/suthome/recshow/:rid', component: StuRecShow },
		],
	},
	{
		path: '/managehome',
		component: ManageHome,
		children: [
			{ path: '/managehome', redirect: '/managehome/manageindex' },
			{ path: '/managehome/manageindex', name: '/managehome/manageindex', component: ManageIndex },
			{ path: '/managehome/shop', component: ShopManage },
			{ path: '/managehome/notes', component: NotesManage },
			{ path: '/managehome/chat', name: 'Im', component: () => import('@/components/Im') },
			{ path: '/managehome/myshop', component: MyShop },
			{ path: '/managehome/shop/recruitment', component: OneRecruitment },
		],
	},
];

const router = new VueRouter({
	routes,
});

// 路由前置守卫
router.beforeEach((to, from, next) => {
	if (to.path === '/login') return next();
	// 获取token
	const tokenStr = sessionStorage.getItem('token');

	if (tokenStr == null) return next('/login');
	next();

	// if (to.path === '') {
	//   console.log(to.path);
	//   console.log(token);
	//   // 如果有token就直接放行 没有就回去登录

	//   if (token !== null) {
	//     next()
	//   } else {
	//     next('/login')
	//   }
	// } else {
	//   // 其他导航正常放行
	//   next()
	// }
});

export default router;
