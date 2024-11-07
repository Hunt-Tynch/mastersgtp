import React, { useState } from 'react';
import { Button, Col, Container, Form, OverlayTrigger, Row, Tooltip } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { putStartTime } from '../service/HuntService';

const SetStartTime = () => {
    const [day, setDay] = useState('');
    const [startTime, setStartTime] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const [hours, minutes] = startTime.split(':').map(Number);
        const timeInMinutes = hours * 60 + minutes;
        try {
            await putStartTime(day, timeInMinutes);
            alert("Start time updated successfully!");
            navigate('/');
        } catch (error) {
            console.error("Failed to set start time:", error);
            alert("Error setting start time.");
        }
    };

    return (
        <Container fluid className="py-4" style={{ backgroundColor: '#f8f9fa', borderRadius: '10px', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)', maxWidth: '500px' }}>
            <h2 className="text-center mb-4">Set Start Time</h2>
            <Form onSubmit={handleSubmit} style={{backgroundColor: '#e9ecef', padding: '20px', borderRadius: '10px' }}>
                <Form.Group as={Row} className="mb-3" controlId="daySelect">
                    <Form.Label column sm="5" className="fw-bold">Select Day:</Form.Label>
                    <Col sm="7">
                        <Form.Select value={day} onChange={(e) => setDay(e.target.value)} required>
                            <option value="">Choose Day...</option>
                            <option value="0">Day Zero</option>
                            <option value="1">Day One</option>
                            <option value="2">Day Two</option>
                            <option value="3">Day Three</option>
                        </Form.Select>
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="startTime">
                    <Form.Label column sm="5" className="fw-bold">Start Time:</Form.Label>
                    <Col sm="7">
                        <Form.Control type="time" value={startTime} onChange={(e) => setStartTime(e.target.value)} required />
                    </Col>
                </Form.Group>
                <OverlayTrigger placement="top" overlay={<Tooltip>Set the start time for the selected day</Tooltip>}>
                    <Button type="submit" variant="primary" className="w-100">Set Start Time</Button>
                </OverlayTrigger>
            </Form>
        </Container>
    );
};

export default SetStartTime;
