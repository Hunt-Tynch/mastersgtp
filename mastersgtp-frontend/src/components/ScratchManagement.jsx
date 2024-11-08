import React, { useEffect, useState } from 'react';
import { Button, Col, Container, Form, OverlayTrigger, Row, Table, Tooltip } from 'react-bootstrap';
import { createScratch, deleteScratch, getScratches } from '../service/ScratchService';

const ScratchManagement = () => {
    const [scratches, setScratches] = useState([]);
    const [newScratch, setNewScratch] = useState({
        judge: { number: '' },
        dog: { number: '' },
        reason: '',
        time: '00:00', // Set as HH:MM format for form input
    });

    // Helper functions to convert between minutes and HH:MM format
    const timeToMinutes = (time) => {
        const [hours, minutes] = time.split(':').map(Number);
        return hours * 60 + minutes;
    };

    const minutesToTime = (minutes) => {
        const hours = String(Math.floor(minutes / 60)).padStart(2, '0');
        const mins = String(minutes % 60).padStart(2, '0');
        return `${hours % 12 === 0 ? '12' : hours % 12}:${mins} ${hours < 12 ? 'AM' : 'PM'}`;
    };

    const fetchScratches = async () => {
        try {
            const response = await getScratches();
            const formattedScratches = response.data.map(scratch => ({
                ...scratch,
                time: minutesToTime(scratch.time), // Convert time to HH:MM format for display
            }));
            setScratches(formattedScratches);
        } catch (error) {
            console.error("Failed to fetch scratches:", error);
        }
    };

    useEffect(() => {
        fetchScratches();
    });


    const handleInputChange = (e) => {
        const { name, value } = e.target;
        
        if (name === "judgeNumber") {
            setNewScratch(prevState => ({
                ...prevState,
                judge: { ...prevState.judge, number: value }
            }));
        } else if (name === "dogNumber") {
            setNewScratch(prevState => ({
                ...prevState,
                dog: { ...prevState.dog, number: value }
            }));
        } else {
            setNewScratch(prevState => ({
                ...prevState,
                [name]: value
            }));
        }
    };

    const handleCreateScratch = async (e) => {
        e.preventDefault();
        try {
            const scratchData = {
                ...newScratch,
                time: timeToMinutes(newScratch.time), // Convert time to minutes before sending to backend
            };
            await createScratch(scratchData);
            setNewScratch({ judge: { number: '' }, dog: { number: '' }, reason: '', time: '00:00' });
            fetchScratches();
        } catch (error) {
            console.error("Failed to create scratch:", error);
        }
    };

    const handleDeleteScratch = async (id) => {
        if (window.confirm("Are you sure you want to delete this scratch?")) {
            try {
                await deleteScratch(id);
                fetchScratches();
            } catch (error) {
                console.error("Failed to delete scratch:", error);
            }
        }
    };

    return (
        <Container fluid className="py-4" style={{ backgroundColor: '#f8f9fa', borderRadius: '10px', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)', maxWidth: '800px' }}>
            <h2 className="text-center mb-4">Scratch Management</h2>

            {/* Scratch Form */}
            <Form onSubmit={handleCreateScratch} className="p-3 mb-4" style={{ backgroundColor: '#e9ecef', borderRadius: '10px' }}>
                <h4 className="text-center mb-3">Add New Scratch</h4>
                <Row className="align-items-center mb-3">
                    <Col xs={4}>
                        <Form.Label>Judge Number:</Form.Label>
                    </Col>
                    <Col xs={8}>
                        <Form.Control
                            type="number"
                            name="judgeNumber"
                            value={newScratch.judge.number}
                            onChange={handleInputChange}
                            placeholder="Judge Number"
                            required
                        />
                    </Col>
                </Row>
                <Row className="align-items-center mb-3">
                    <Col xs={4}>
                        <Form.Label>Dog Number:</Form.Label>
                    </Col>
                    <Col xs={8}>
                        <Form.Control
                            type="number"
                            name="dogNumber"
                            value={newScratch.dog.number}
                            onChange={handleInputChange}
                            placeholder="Dog Number"
                            required
                        />
                    </Col>
                </Row>
                <Row className="align-items-center mb-3">
                    <Col xs={4}>
                        <Form.Label>Reason:</Form.Label>
                    </Col>
                    <Col xs={8}>
                        <Form.Control
                            type="text"
                            name="reason"
                            value={newScratch.reason}
                            onChange={handleInputChange}
                            placeholder="Reason"
                            required
                        />
                    </Col>
                </Row>
                <Row className="align-items-center mb-3">
                    <Col xs={4}>
                        <Form.Label>Time (HH:MM):</Form.Label>
                    </Col>
                    <Col xs={8}>
                        <Form.Control
                            type="time"
                            name="time"
                            value={newScratch.time}
                            onChange={handleInputChange}
                            required
                        />
                    </Col>
                </Row>
                <Button type="submit" variant="primary" className="w-100">Add Scratch</Button>
            </Form>

            {/* Scratch List */}
            <h4 className="text-center mb-3">Existing Scratches</h4>
            <Table striped bordered hover responsive>
                <thead>
                    <tr>
                        <th>Judge</th>
                        <th>Dog</th>
                        <th>Reason</th>
                        <th>Time</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {scratches.map((scratch) => (
                        <tr key={scratch.id}>
                            <td>{scratch.judge.number}</td>
                            <td>{scratch.dog.number}</td>
                            <td>{scratch.reason}</td>
                            <td>{scratch.time}</td>
                            <td>
                                <OverlayTrigger placement="top" overlay={<Tooltip>Delete Scratch</Tooltip>}>
                                    <Button variant="danger" size="sm" onClick={() => handleDeleteScratch(scratch.id)}>
                                        Delete
                                    </Button>
                                </OverlayTrigger>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </Container>
    );
};

export default ScratchManagement;
