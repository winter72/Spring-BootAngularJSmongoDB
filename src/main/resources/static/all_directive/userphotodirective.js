angular.module('homeApp')
.directive('updatePhoto',['$http','API_ENDPOINT','AuthService',function ($http,API_ENDPOINT,AuthService) {
   return{
       templateUrl:'directives/updatephoto.html',
       link:function (scope,element,attributes) {

           let fl=element.find('input').eq(0);
           let ck=element.find('p').eq(0);
           var fd = new FormData();
           scope.checkings=false;
           fl.on("change", function(event) {
               var reader = new FileReader();
               reader.onload = function(){
                   var output = document.getElementById('images');
                   output.src = reader.result;
                   scope.fotolink.photolink =reader.result;
                   //fd.append('file',scope.fotolink.photolink );
                  // console.log('File reader:',output.src);
               };
               var file=event.target.files[0];
               reader.readAsDataURL(event.target.files[0]);
               ck.css('visibility','visible');

               fd.append('file',event.target.files[0]);//reader.result);
               fd.append('userid',scope.fotolink.userlink)
           });

           scope.updateImg=function () {

               $http.post(API_ENDPOINT.urlFile,fd,{transformRequest: angular.identity,
                   headers: {'Content-Type': undefined},
                  transformResponse: function (data) {
                    /*  var formData = new FormData();
                       formData.append('model',data);
                      console.log(data);*/
                       return data;//formData;
                   }
               })
                 .then(function callSuccess(resp) {
                     console.log(resp['data']);
                     AuthService.setUserFoto(resp['data']);
                     AuthService.setFoto(resp['data']);
                     alert('success');
                 },function callErr(resp) {
                     console.log(resp['status']);
                     alert('error');
                 })
           }
       }
   }
}]);

