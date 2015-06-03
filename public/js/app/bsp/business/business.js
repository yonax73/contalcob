window.onload = function(){
  init();	
}

function init(){	
	if(VIEW_ACTION.CREATE_BUSINESS){
       hiddenContentNavigation();			
       document.getElementById('page-title-txt').textContent = 'Registrar Negocio';
	}else{
      swicthActiveLink(document.getElementById('business-nav'));
      hiddenElements();
      initApp();
      loadBusiness();
	}  
   formValidation();
}
function disabledControls(value){
	document.getElementById('save-business-btn').disabled = value;
}
function hiddenElements(){
	document.getElementById('password-content').classList.add('hidden');
	document.getElementById('confirmPassword-content').classList.add('hidden');
	document.getElementById('acceptTerms-content').classList.add('hidden');
}
function loadBusiness(){
	   Pace.track(function(){    	
		$.ajax({
	        type: 'GET',
	        url: '/load-business',               
	        success: function (response) {
	            try {	                
	                fillBusiness(JSON.parse(response));					           
	            } catch (ex) {
	                console.error(ex);
	                alert(response);
	            }
	        },
	        error: function (xhr) {
	            alert(xhr.statusText);
	        },
	        timeout: 60000
	    });
	   });  
}
function fillBusiness(b){  
	document.getElementById('businessName').value = b.name;
	document.getElementById('userName').value = b.user.name;
	document.getElementById('emailLogin').value = b.user.login.email;
	document.getElementById('userLogin').value = b.user.login.user;
}
function formValidation(){
	// FORM  VALIDATION
	// =================================================================
	$('#business-frm').bootstrapValidator({ 
	        message: 'Este campo es requerido',
			feedbackIcons: faIcon,
			fields: {
			businessName: {
				message: 'El nombre del negocio no es valido',
				validators: {
					notEmpty: {
						message: 'El nombre del negocio es requerido.'
					}
				}
			},
			userName: {
				validators: {
					notEmpty: {
						message: 'El nombre es requerido'
					}
				}
			},
		    emailLogin: {
			   validators: {
				notEmpty: {
					message: 'El correo el&eacute;ctronico es requerido'
				},
				emailAddress: {
					message: 'Ingrese un correo el&eacute;ctronico valido'
				 }
			   }
		    },
			userLogin: {
				validators: {
					notEmpty: {
						message: 'El nombre de usuario es requerido'
					}
				}
			},			
			password: {
				validators: {
					notEmpty: {
						message: 'La contrase&ntilde;a es requerida'
					},
					identical: {
						field: 'confirmPassword',
						message: 'La contrase&ntilde;a y la confirmaci&oacute;n no son iguales'
					}
				}
			},
			confirmPassword: {
				validators: {
					notEmpty: {
						message: 'La confirmaci&oacute;n de la contrase&ntilde;a es requerida'
					},
					identical: {
						field: 'password',
						message: 'La contrase&ntilde;a y la confirmaci&oacute;n no son iguales'
					}
				}
			},
			acceptTerms: {
				validators: {
					notEmpty: {
						message: 'Debes aceptar terminos, condiciones y politicas de privacidad'
					}
				}
			}
		}
	}).on('success.field.bv', function(e, data) {
		var $parent = data.element.parents('.form-group');	
		$parent.removeClass('has-success');
	}).on('success.form.bv', function(e) {           
          e.preventDefault();
          var $form = $(e.target); 
          disabledControls(true);
          Pace.track(function(){             
            $.ajax({
                url: $form.attr('action'),
                type: 'POST',
                data: $form.serialize(),
                success: function(response) {
                    if(response === RESPONSE_OK){                       
                       if(VIEW_ACTION.CREATE_BUSINESS){
                       	Modal.Alert.show();
                         Modal.Alert.action().onclick =function(){
                         	redirectLogin();                         	
                         }
                        }else{
                         Notification.success();
                       }

                    }else{
                    	alert(response);
                    }
                    disabledControls(false);
                },
                error: function(response){
                	disabledControls(false);
                	alert(response.responseText);
                }
            });
          });
        });
}

function redirectLogin(){	
   window.location = '/';	
}
