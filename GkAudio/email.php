<?php

function spamcheck($field) {
  // Sanitize e-mail address
  $field=filter_var($field, FILTER_SANITIZE_EMAIL);
  // Validate e-mail address
  if(filter_var($field, FILTER_VALIDATE_EMAIL)) {
    return TRUE;
  } else {
    return FALSE;
  }
}

?>
<?php
if(isset($_POST['email'])) {
  $valid_email = spamcheck($_POST['email']);
  if ($valid_email==TRUE) {
	  $email_from = $_POST['email'];
	  $email_subject = $_POST['subject'];

	  $email_content = $_POST['name'] . " wrote the following: \n" . $_POST["message"];
	  $email_content = wordwrap($email_content, 70);

	  mail("info@gk-audio.com",$email_subject,$email_content,"From: $email_from\n");

	  echo "OK";
  }
}
?>