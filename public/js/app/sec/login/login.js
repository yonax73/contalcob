window.onload = function(){
  init();	
}

function init(){	 
  formValidation();
}

function formValidation(){
	// FORM  VALIDATION
	// =================================================================
	$('#sign-in-frm').bootstrapValidator({ 
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
						}			
					}
				}
			}			
	}).on('success.field.bv', function(e, data) {
		var $parent = data.element.parents('.form-group');	
		$parent.removeClass('has-success');
	}).on('success.form.bv', function(e) {           
            e.preventDefault();
            document.getElementById('loginType').value = Login.LENDER;
            var $form = $(e.target);    
            Pace.track(function(){
              $.ajax({
                url: $form.attr('action'),
                type: 'POST',
                data: $form.serialize(),
                success: function(response) {
                    if(response === RESPONSE_OK){                           
                    	   VIEW_ACTION.CREATE_BUSINESS = false; 
                           window.location = '/dashboard';
                    }else{
                    	alert(response);
                    }
                },
                error: function(response){
                	alert(response.responseText);
                }
              });
		    });
        });
}


