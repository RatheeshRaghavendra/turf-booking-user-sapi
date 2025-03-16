# Turf Booking User Sapi
User System API for the Turf Booking Application

## About the Integration
You can read about it [here](https://github.com/RatheeshRaghavendra/My-Projects/blob/main/Turf-Booking-Service.md)

## End Points

### GET /user/{userId}

Returns the User Details using the User ID

### POST /user

Adds a User

### PUT /user

Updates a User

### DELETE /user/{userId}

Deletes the User with the User ID

## Object Structures

### ApiResponse

```json
{
    "statusCode": 200,
    "statusMessage": "OK",
    "payload": {
    
    },
    "apiError": null
}
```

### ApiError

```json
{
	"errorMessage": "No value present",
	"errorDescription": "java.util.NoSuchElementException: No value present",
	"customError": "No Turf with the Turf ID: 5, Present in the DB"
}
```

### UserDetails

```json
{
	"userId": 1,
	"username": "ouji",
	"password": "123",
	"firstName": "Ratheesh",
	"lastName": "Raghavendra",
	"phoneNumber": "1234567890",
	"emailId": "something@email.com"
}
```
