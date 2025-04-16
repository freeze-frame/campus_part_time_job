import $http from '@/utils/Request.js';

// 选择性暴露接口
// 登录api
export const loginResultAPI = (loginKey) => {
	return $http.post('/login', loginKey);
};

//注册api
export const addUserAPI = (userData) => {
	return $http.post('/register', userData);
};

//获取学生信息列表
export const stuShowAPI = (currentpage, pagesize) => {
	return $http({
		url: '/stu/show',
		method: 'get',
		params: {
			currentpage,
			pagesize,
		},
	});
};

//修改学生信息
export const stuEditAPI = (body) => {
	return $http.post('/stu/edit', body);
};

//删除学生信息
export const stuDeleteAPI = (body) => {
	return $http.post('/stu/delete', body);
};

//搜索学生信息
export const stuSearchAPI = (currentpage, pagesize, search) => {
	return $http({
		url: `/stu/search`,
		method: 'get',
		params: {
			currentpage,
			pagesize,
			search,
		},
	});
};

//通过商家id获取店铺信息
export const shopShowById = (id) => {
	return $http.get(`/managehome/shop/${id}`);
};

//增加商店信息
export const addShopInfo = (body) => {
	return $http.post('/managehome/shop/add', body);
};

//修改商店信息(商家端)
export const editShopInfo = (body) => {
	return $http.post(`/managehome/shop/edit`, body);
};

//删除商店
export const deleteShopInfo = (id) => {
	return $http.get(`shop/del/${id}`);
};

//修改商店信息(管理员端)
export const editShopIdenInfo = (body) => {
	return $http.post(`/shop/edit`, body);
};

//通过店铺id获取招聘信息
export const recruitmentShowByBid = (bid) => {
	return $http.get(`/managehome/shop/recruitment/${bid}`);
};

//增加招聘
export const addRecruitmentInfo = (body) => {
	return $http.post('/managehome/shop/recruitment/add', body);
};
//删除招聘
export const deleteRecruitmentInfo = (id) => {
	return $http.get(`/managehome/shop/recruitment/del/${id}`);
};
//修改招聘
export const editRecruitmentInfo = (body) => {
	return $http.post(`/managehome/shop/recruitment/edit`, body);
};
//海报操作

export const RecruitmentPosterExistAPI = (rid) => {
	return $http.get(`/recruitment/poster/exist/${rid}`);
};
export const RecruitmentPosterAPI = (body) => {
	return $http.post(`/recruitment/poster/add`, body);
};

//获取商家信息列表
export const businessShowAPI = (currentpage, pagesize) => {
	return $http({
		url: '/business/show',
		method: 'get',
		params: {
			currentpage,
			pagesize,
		},
	});
};

//修改商家信息列表
export const businessEditAPI = (body) => {
	return $http.post('/business/edit', body);
};


//获取商家信息列表
export const businessDelAPI = (uid) => {
	return $http.get(`/business/del/${uid}`);
};

export const businessSearchAPI = (currentpage, pagesize, search) => {
	return $http({
		url: '/business/search',
		method: 'get',
		params: {
			currentpage,
			pagesize,
			search,
		},
	});
};

//获取商店信息列表
export const shopShowAPI = (currentpage, pagesize) => {
	return $http({
		url: '/shop/show',
		method: 'get',
		params: {
			currentpage,
			pagesize,
		},
	});
};

//获取商店的认证列表
export const shopIdenShowAPI = (currentpage, pagesize) => {
	return $http({
		url: '/shop/shopIdentity',
		method: 'get',
		params: {
			currentpage,
			pagesize,
		},
	});
};

//修改商家信息列表
export const shopEditAPI = (body) => {
	return $http.post('/shop/edit', body);
};

export const shopDelAPI = (uid) => {
	return $http.get(`/shop/del/${uid}`);
};
export const shopSearchAPI = (currentpage, pagesize, search) => {
	return $http({
		url: '/shop/search',
		method: 'get',
		params: {
			currentpage,
			pagesize,
			search,
		},
	});
};

//获取简历信息列表
export const recruitmentShowAPI = () => {
	return $http.get('/recruitment/show');
};

//修改简历信息列表
export const recruitmentEditAPI = (body) => {
	return $http.post('/recruitment/edit', body);
};

//获取简历信息列表
export const recruitmentDelAPI = (uid) => {
	return $http.get(`/recruitment/del/${uid}`);
};

export const recruitmentSearchAPI = (search) => {
	return $http.get(`/recruitment/search/${search}`);
};

//学生端 获取所有招聘信息
export const getRecruitmentAPI = () => {
	return $http.get('/suthome/stuindex/getrec');
};

//学生端 获取详细信息
export const getRecruitmentInfoByidAPI = (id) => {
	return $http.get(`/suthome/stuindex/recshow/${id}`);
};

//增加热度
export const addRecHeatAPI = (rid) => {
	return $http.get(`/suthome/stuindex/addheat/${rid}`);
};

//获取头条数据
export const getTopAPI = () => {
	return $http.get(`/suthome/stuindex/bannertop`);
};

//学生搜索关键词获取招聘信息
export const getSearchRecInfoAPI = (search) => {
	return $http.get(`suthome/stuindex/search/${search}`);
};

// 学生获取个人信息
export const getInfoAPI = (uid) => {
	return $http({
		url: 'suthome/stuindex/myinfo',
		method: 'get',
		params: {
			uid,
		},
	});
};

//学生修改头像
export const editStuImg = (body) => {
	return $http.post('/stu/editimg', body);
};

//修改密码
// export const editStuPassword = (uid,oldPassword,password) => {
//   return $http.post('/stu/edit/password', (uid,oldPassword,password))
// }
//修改密码
export const editStuPassword = (uid, oldPassword, password) => {
	return $http({
		url: '/stu/edit/password',
		method: 'post',
		data: {
			uid,
			oldPassword,
			password,
		},
	});
};

//上传简历
export const stuUploadNotesAPI = (uid, body) => {
	return $http.post(`/stu/notes/upload/${uid}`, body);
};

//获取简历
export const getNotesAPI = (uid) => {
	return $http.get(`/stu/notes/${uid}`);
};

//下载简历
export const downloadNotesAPI = (fileName) => {
	return $http({
		url: `/stu/notes/download/${fileName}`,
		method: 'get',
		response: 'blob',
	});
};

//删除简历
export const stuDelNotesAPI = (uid, fileName) => {
	return $http({
		url: `/stu/notes/del`,
		method: 'post',
		params: {
			uid,
			fileName,
		},
	});
};

//通过简历id展现出对应的评论
export const commentShowByRecIdAPI = (rid) => {
	return $http({
		url: `/stu/rec/comment/${rid}`,
		method: 'get',
	});
};

//增加一级评论
export const addCommentAPI = (body) => {
	return $http.post('/stu/rec/add/comment', body);
};

//增加二级评论 前端把com_id存入answer_id 外键
export const addCommentReplyAPI = (body) => {
	return $http.post('/stu/rec/add/reply', body);
};

//删除指定父级评论
export const delCommentAPI = (cid) => {
	return $http.get(`/stu/rec/del/comment/${cid}`);
};
//删除指定子级评论
export const delCommentReplyAPI = (rid) => {
	return $http.get(`/stu/rec/del/reply/${rid}`);
};

//投递简历
export const sendNotesBnAPI = (body) => {
	return $http.post('/stu/notes/send', body);
};

//删除指定子级评论
export const getMyAllNotesAPI = (id) => {
	return $http.get(`/managehome/rec/notes/${id}`);
};

//获取所有学生简历
export const getStuNotesAPI = () => {
	return $http.get(`/stu/notes/manage`);
};

//管理员删除商家收到的简历
export const delNotesByRecAPI = (nid) => {
	return $http({
		url: `/rec/notes/del`,
		method: 'post',
		params: {
			nId: nid,
		},
	});
};

//获取所有招聘和他的所有评论
export const getCommentAPI = () => {
	return $http.get(`/rec/comment`);
};

//学生获取自己投递的简历
export const getMyDeliverAPI = (uid) => {
	return $http.get(`/stu/deliver/${uid}`);
};

//通过告知学生
export const changeNotesInfoAndStateAPI = (body) => {
	return $http.post(`/managehome/notes/change`, body);
};

//点击查看修改状态码
export const changeState = (nid) => {
	return $http.get(`/notes/change/state/${nid}`);
};
