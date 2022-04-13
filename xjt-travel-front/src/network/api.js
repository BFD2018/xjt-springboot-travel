import {$getRequest,$postKeyValueRequest,$postRequest} from "./request";

/*登录、注册*/
export const _toLogin = (data) =>{
	return $postRequest("/user/login",data);
}

export const _toRegister = (data) =>{
	return $postRequest("/user/register",data);
}

//请求所有博客
export const _getAllBlogs = (current=1,size=6) =>{
	return $getRequest("/blog/getByPage?current="+current + "&size="+size);
}

//分类
export const _getAllType = () =>{
	return $getRequest("/type/all");
}

//博客详情
export const _getBlogDetailByBlogId = (bid) =>{
	return $getRequest("/blog/detail?blog_id="+bid);
}



