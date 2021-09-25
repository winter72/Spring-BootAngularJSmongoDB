angular.module('homeApp')
    .directive("updateDirective",[ function(){
        return {
            templateUrl:'directives/updateuser.html',
            link: function f(scope,element,attributes) {
                let dts=scope[attributes['updateDirective']];
                let dta=element.find('input');
                let bnt=element.find('button');
                let op;
                let m;
                let n;
                let c;
                scope.msg='';
                for(let i=0;i<dta.length;i++){
                    dta.eq(i).css('border','1px solid black');
                    if(i===dta.length-4){
                        op=dta.eq(i);
                    }
                    if(i===dta.length-3){
                        m=dta.eq(i);
                    }
                    else if(i===dta.length-2){
                        n= dta.eq(i);
                    }
                    else  if(i===dta.length-1){
                        c= dta.eq(i);
                     }

                }

                scope.opChange=function(){
                      //  console.log(dts.firstname);
                    if(op.val().length>1 && n.val().length===0 && m.val().length===0||op.val().length>1 && n.val()===m.val()) {
                     //   scope.objStyle.cursor = 'pointer';
                        scope.valids=false;
                    }else {
                       // scope.objStyle.cursor='no-drop';
                        scope.valids=true;
                    }

                }

                scope.nKeyup=function () {

                    if(n.val()===m.val() && n.val().length>0 && m.val().length>0 && op.val().length>1){
                        n.css('color','green');
                        m.css('color','green');
                        n.css('border','2px solid darkgreen');
                        m.css('border','2px solid darkgreen');
                       // scope.objStyle.cursor='pointer';
                        scope.valids=false;
                    }else if(n.val()===m.val() && n.val().length>0 && m.val().length>0){
                        n.css('color','green');
                        m.css('color','green');
                        n.css('border','2px solid darkgreen');
                        m.css('border','2px solid darkgreen');
                     //   scope.objStyle.cursor='no-drop';
                        scope.valids=true;
                    }else if(n.val().length===0 && m.val().length===0&&op.val().length>1 ){
                        n.css('border','1px solid black');
                        m.css('border','1px solid black');
                     //   scope.objStyle.cursor = 'pointer';
                        scope.valids=false;
                    }else if( n.val().length===0 && m.val().length===0){
                        n.css('border','1px solid black');
                        m.css('border','1px solid black');
                       // scope.objStyle.cursor = 'no-drop';
                        scope.valids=true;
                    } else{
                        n.css('color','red');
                        m.css('color','red');
                        n.css('border','2px solid red');
                        m.css('border','2px solid red');
                      //  scope.objStyle.cursor='no-drop';
                        scope.valids=true;
                    }
                };

            }
        }
    }]);