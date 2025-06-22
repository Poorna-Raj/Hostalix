document.addEventListener('DOMContentLoaded', function () {
	const tableBody = document.querySelector('#roomTable tbody');
	const editPopup = document.getElementById('popup');
	const updateRoomForm = document.getElementById('addRoomForm'); 
	const closeRoomForm = document.getElementById('addRoomClose');
	const addRoomButton = document.getElementById('NEWaddRoomBtn');
	const addRoomClose = document.getElementById('addRoomCloseBtn');
	const addRoomForm = document.getElementById('NewaddRoomForm');
	const filterForm = document.getElementById('filterForm');
	
	fetch('/rooms')
	  .then(async response => {
	    const contentType = response.headers.get('content-type');
	    console.log('Status:', response.status, 'Content-Type:', contentType);

	    const data = await response.json();
	    console.log('Received data:', data);

	    if (!Array.isArray(data.data)) throw new Error('Expected array of rooms');
		const rooms = data.data;
	    
	    populateRoomTable(rooms);
	  })
	  
	  
	  tableBody.addEventListener('click', async function(event) {
		const target = event.target;
		if (target.classList.contains('edit')){
			const roomId = target.getAttribute('data-roomId');
			try{
				const response = await fetch(`/rooms/${roomId}`);
				if (!response.ok) {
                    throw new Error(`Error fetching room details: ${response.message}`);
                }
				const room = await response.json();
				if(room && room.data) {
                    const roomData = room.data;
                    updateRoomForm.roomId.value = roomData.roomId;
                    updateRoomForm.roomName.value = roomData.roomName;
                    updateRoomForm.roomType.value = roomData.roomType;
                    updateRoomForm.bedType.value = roomData.bedType;
                    updateRoomForm.max_occupancy.value = roomData.maxOccupancy;
                    updateRoomForm.bedCount.value = roomData.bedCount;
                    updateRoomForm.pricePerNight.value = roomData.pricePerNight.toFixed(2);
                    updateRoomForm.location.value = roomData.location;
                    updateRoomForm.floorNumber.value = roomData.floorNumber;
                    updateRoomForm.hasWiFi.checked = roomData.hasWiFi;
                    updateRoomForm.hasAC.checked = roomData.hasAC;
					updateRoomForm.available.value = roomData.available 
					
					
					popup.style.display = 'flex';

                }
				else{
				    throw new Error('Room data not found');
                }
			}
			catch(error){
                console.error('Error fetching room details:', error);
            }
		} 
		else if(target.classList.contains('delete')){
			const roomId = target.getAttribute('data-roomId');
			if(confirm('Are you sure you want to delete this room?')){
                try{
                    const response = await fetch(`/rooms/${roomId}`, {
                        method: 'DELETE'
                    });
                    if (!response.ok) {
                        throw new Error(`Error deleting room: ${response.statusText}`);
                    }
                    alert('Room deleted successfully!');
                    location.reload();
                }
                catch(error){
                    console.error('Error deleting room:', error);
                }
            }
 		}
	  });
	  addRoomClose.addEventListener('click', function(){
	  		popup.style.display = "none";
	  });
	  updateRoomForm.addEventListener('submit', async function(event){
		event.preventDefault();
		const hasWiFi = document.getElementById('hasWiFi').checked;
		const hasAC = document.getElementById('hasAC').checked;
		const formData = {
			roomId: updateRoomForm.roomId.value,
			roomName: updateRoomForm.roomName.value,
			roomType: updateRoomForm.roomType.value,
			bedType: updateRoomForm.bedType.value,
			maxOccupancy: parseInt(updateRoomForm.max_occupancy.value, 10),
			bedCount: parseInt(updateRoomForm.bedCount.value, 10),
			pricePerNight: parseFloat(updateRoomForm.pricePerNight.value),
			location: updateRoomForm.location.value,
			floorNumber: parseInt(updateRoomForm.floorNumber.value, 10),
			hasWiFi: hasWiFi,
			hasAC: hasAC,
			available: updateRoomForm.available.value
		}
		try{
			const response = await fetch('/rooms',{
				method: 'PUT',
				headers: {
					'Content-Type':'application/json'
				},
				body: JSON.stringify(formData)
			});
			const result = await response.json();
			if (!response.ok) {
                throw new Error(`Error updating room: ${result.message}`);
            }
			alert('Room updated successfully!');
			popup.style.display = 'none';
			location.reload();
			
		}
		catch(error){
			console.error('Error updating room:', error);
		}
	  });
	  closeRoomForm.addEventListener('click', function() {
		editPopup.style.display = 'none';
	  });
	  addRoomButton.addEventListener('click', function() {
		const addRoomPopup = document.getElementById('addRoomPopup');
		addRoomPopup.style.display = 'flex';
	  });
	  addRoomClose.addEventListener('click', function(){
		const addRoomPopup = document.getElementById('addRoomPopup');
		addRoomPopup.style.display = 'none';
	  });
	  addRoomForm.addEventListener('submit', async function(event){
		event.preventDefault();
		
		const formData = {
			roomName: addRoomForm.roomName.value,
			roomType: addRoomForm.roomType.value,
			bedType: addRoomForm.bedType.value,
			maxOccupancy: parseInt(addRoomForm.max_occupancy.value, 10),
			bedCount: parseInt(addRoomForm.bedCount.value, 10),
			pricePerNight: parseFloat(addRoomForm.pricePerNight.value),
			location: addRoomForm.location.value,
			floorNumber: parseInt(addRoomForm.floorNumber.value, 10),
			hasWiFi: addRoomForm.hasWiFi.checked,
			hasAC: addRoomForm.hasAC.checked,
			available: addRoomForm.available.value
		}
		try{
			const response = await fetch('/rooms',{
				method: 'POST',
				headers: {
                    'Content-Type': 'application/json'
                },
				body: JSON.stringify(formData)
			});
			const result = await response.json();
			if (!response.ok) {
                throw new Error(`Error adding room: ${result.message}`);
            }
			alert('Room added successfully!');
			location.reload();
		}
		catch(error){
			console.error('Error adding room:', error);
		}
	  });
	  
	  filterForm.addEventListener('submit', async function(event) {
	  	event.preventDefault();

	  	let name = filterForm.searchName.value.trim();
	  	let available = filterForm.status.value;
	  	let order = filterForm.order.value;

	  	// Convert strings to appropriate types
	  	if (available === 'Available') {
	  		available = true;
	  	} else if (available === 'Not Available') {
	  		available = false;
	  	} else {
	  		available = ''; // let the backend ignore this
	  	}

	  	if (order === 'price_asc') {
	  		order = 1;
	  	} else if (order === 'price_desc') {
	  		order = 2;
	  	} else {
	  		order = ''; // no order preference
	  	}

	  	// Build query string dynamically
	  	const params = new URLSearchParams();
	  	if (name) params.append('name', name);
	  	if (available !== '') params.append('available', available);
	  	if (order !== '') params.append('order', order);

	  	try {
	  		const response = await fetch(`/rooms/filter?${params.toString()}`);
	  		const result = await response.json();

	  		if (!response.ok) {
	  			throw new Error(`Error filtering rooms: ${result.message}`);
	  		}

	  		if (Array.isArray(result.data)) {
	  			populateRoomTable(result.data);
	  		} else {
	  			throw new Error('Expected array of rooms after filtering');
	  		}
	  	} catch (error) {
	  		console.error('Error filtering rooms:', error);
	  	}
	  });

  });
  function populateRoomTable(rooms) {
	const tableBody = document.querySelector('#roomTable tbody');
  		tableBody.innerHTML = '';
  		rooms.forEach(room => {
  			const row = document.createElement('tr');
  			row.innerHTML = `
  				<td>${room.roomId}</td>
  				<td>${room.roomName}</td>
  				<td>${room.roomType}</td>
  				<td>${room.bedType}</td>
  				<td>${room.maxOccupancy}</td>
  				<td>${room.bedCount}</td>
  				<td>LKR ${room.pricePerNight.toFixed(2)}</td>
  				<td>${room.location}</td>
  				<td>${room.floorNumber}</td>
  				<td>${room.hasWiFi ? 'Yes' : 'No'}</td>
  				<td>${room.hasAC ? 'Yes' : 'No'}</td>
  				<td>
  					<span class="availability ${room.available ? 'available' : 'unavailable'}">
  						${room.available ? 'Available' : 'Not Available'}
  					</span>
  				</td>
  				<td>
  					<button class="action-btn edit" data-roomId="${room.roomId}">Edit</button>
  					<button class="action-btn delete" data-roomId="${room.roomId}">Delete</button>
  				</td>
  			`;
  			tableBody.appendChild(row);
  		});
  	}