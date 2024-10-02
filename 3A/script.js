const resumeForm = document.getElementById("resumeForm");

resumeForm.addEventListener("submit", (event) => {
  event.preventDefault();

  const name = document.getElementById("name").value;
  const dob = document.getElementById("dob").value;
  const address = document.getElementById("address").value;
  const education = document.getElementById("education").value;
  const phone = document.getElementById("phone").value;
  const skills = document.getElementById("skills").value;
  const resumeFile = document.getElementById("resumeFile").files[0];

  if (!/^[A-Z][a-zA-Z]+$/.test(name)) {
    alert("Name must start with a capital letter and contain only alphabets.");
    return;
  }

  if (!/^\d{10}$/.test(phone)) {
    alert("Please enter a valid 10-digit phone number.");
    return;
  }

  const resumeData = {
    name: name,
    dob: dob,
    address: address,
    education: education,
    phone: phone,
    skills: skills,
  };

  localStorage.setItem("resumeData", JSON.stringify(resumeData));

  alert("Resume submitted successfully!");
});
