/**
 * google login
 */
function onloadFunction(){
	gapi.client.setApiKey('AIzaSyCilwEGGPZnzvSE737V3AZZDiZnwxYc6fc');
   gapi.client.load('plus','v1',function() {});
}