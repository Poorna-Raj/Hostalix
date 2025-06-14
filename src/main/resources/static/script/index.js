const API_BASE = 'http://localhost:8080';
document.getElementById('loginForm').addEventListener('submit', async function(event) {
    // Prevent form submission
    event.preventDefault();
    
	const emailInput = document.getElementById('email');
	const passwordInput = document.getElementById('password');
	
	if(validateEmail(emailInput)) {
        const email = emailInput.value.trim();
        const password = passwordInput.value.trim();
        
        try {
            const response = await fetch(`${API_BASE}/users`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, password })
            });
            
            if (response.ok) {
                const data = await response.json();
                const role = data.role;
				switch (role){
					
				    case 'Manager':
				        window.location.href = '../manager.html';
                        break;
                    default:
                        alert('Unknown role. Please contact support.');
                }
            } else {
                const errorData = await response.json();
                alert(errorData.message || 'Login failed. Please try again.');
            }
        } catch (error) {
            console.error('Error during login:', error);
            alert('An error occurred. Please try again later.');
        }
    }
});
        
function validateEmail() {
    const emailInput = document.getElementById('email');
    const emailError = document.getElementById('emailError');
    const email = emailInput.value.trim();
    
    if (!email) {
        emailError.textContent = 'Email is required';
        emailInput.classList.add('error-border');
        return false;
    }
    
    if (!isValidEmail(email)) {
        emailError.textContent = 'Please enter a valid email address';
        emailInput.classList.add('error-border');
        return false;
    }
    
    return true;
}
        
function isValidEmail(email) {
    // Simple email regex pattern
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}