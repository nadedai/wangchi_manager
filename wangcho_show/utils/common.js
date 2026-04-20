import { getShareCover } from '@/api/api.js'

export async function share(path) {
	let url = await getShareCover();
	return {
		title: '万驰家居',
		path: path,
		imageUrl: url.msg + '?x-oss-process=image/resize,p_20'
	}
}