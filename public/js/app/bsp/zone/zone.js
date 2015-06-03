window.onload = function(){
  init();	
}

function init(){	
    swicthActiveLink(document.getElementById('zone-nav'));  
    Location.Content.landline.hidden(true);
    Location.Content.extensionLandline.hidden(true);
    Location.Content.mobilePhone.hidden(true);
    Location.Content.address.hidden(true);
    initApp();
    formValidation();
    if(Zone.getId()>0){
       load();  
    }    
}

function load(){
  Pace.track(function(){     
    $.ajax({
          type: 'GET',
          url: '/load-zone?id='+Zone.getId(),               
          success: function (response) {
            try {                 
              fillData(JSON.parse(response));          
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

function fillData(zone){
    var  location = zone.location;
    //zone
    Zone.title(zone.name);
    Zone.setName(zone.name);
    Zone.setDescription(zone.description);
    Zone.setId(zone.id);
    //location
    Location.setId(location.id);
    //address
    Location.Address.setId(location.address.id);
    Location.Address.setNeighBorhood(location.address.neighborhood);
    Location.Address.setAddress(location.address.address);
    //city
    Location.Address.City.setId(location.address.city.id);
    Location.Address.City.setCity(location.address.city.city);
    Location.Address.City.setCountry(location.address.city.country);
}

function formValidation(){
  // FORM  VALIDATION
  // =================================================================
  $('#zone-frm').bootstrapValidator({       
      feedbackIcons: faIcon,
      fields: {
      zoneName: {        
        validators: {
          notEmpty: {
            message: 'El nombre de la zona es requerido.'
          }
        }
      },
      neighborhood: {
        validators: {
          notEmpty: {
            message: 'El barrio es requerido'
          }
        }
      },      
      city: {
        validators: {
          notEmpty: {
            message: 'La ciudad es requerida'
          }
        }
      },
      country: {
        validators: {
          notEmpty: {
            message: 'El Pa&iacute;s es requerido'
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
                   disabledControls(false);
                  try{
                      fillData(JSON.parse(response));                      
                      Modal.Confirm.show();
                      Modal.Confirm.action().onclick= function(){
                        newZone();
                        Modal.Confirm.close();
                      };       
                      //successAlertPage('<strong>Bien hecho!</strong> Los datos se guardaron con &eacute;xito.');                                    
                  }catch(ex){                     
                    console.log(ex);
                    disabledControls(false);
                    alert(response);
                  }
                },
                error: function(response){
                  disabledControls(false);
                  alert(response.responseText);
                }
            });
          });
        });
}

function disabledControls(value){
  document.getElementById('save-zone-btn').disabled = value;
}

function newZone(){
  Zone.clear();
  Location.clear();
}

  