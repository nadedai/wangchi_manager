const baseUrl = 'https://hhttplay.top/prod-api'
// const baseUrl = 'http://localhost:8080'

export default function request(options) {
	return new Promise((resolve, reject) => {
		uni.request({
			...options,
			url: baseUrl + options.url,
			success: (res) => {
				resolve(res.data);
			},
			fail: (err) => {
				uni.showToast({
					title: '失败',
					icon: 'error'
				});

				reject(err);
			}
		});
	});
}