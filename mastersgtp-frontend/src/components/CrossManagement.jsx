import React, { useEffect, useState } from 'react';
import { Button, Col, Container, Form, OverlayTrigger, Row, Table, Tooltip } from 'react-bootstrap';
import { deleteCross, getAllCrossForDay, getCrossByDog, getCrossByJudge, postCross, putCross } from '../service/CrossService';

const CrossManagement = () => {
    const [crosses, setCrosses] = useState([]);
    const [newCross, setNewCross] = useState({ judgeNumber: '', dogNumbers: '', day: '', crossTime: '' });
    const [editMode, setEditMode] = useState(false);
    const [editCrossId, setEditCrossId] = useState(null);
    const [selectedDay, setSelectedDay] = useState('');

    useEffect(() => {
        if (selectedDay) {
            loadCrossesForDay(selectedDay);
        }
    }, [selectedDay]);

    const loadCrossesForDay = async (day) => {
        try {
            const response = await getAllCrossForDay(day);
            setCrosses(response.data);
        } catch (error) {
            console.error("Error loading crosses for the selected day:", error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewCross({ ...newCross, [name]: value });
    };

    // Convert HH:MM format to minutes
    const timeToMinutes = (time) => {
        const [hours, minutes] = time.split(':').map(Number);
        return hours * 60 + minutes;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const crossData = {
            judge: { number: newCross.judgeNumber },
            dogs: newCross.dogNumbers.split(',').map(number => ({ number: parseInt(number.trim()) })),
            day: parseInt(newCross.day),
            crossTime: timeToMinutes(newCross.crossTime)
        };

        try {
            if (editMode) {
                await putCross(editCrossId, crossData);
                alert("Cross updated successfully!");
            } else {
                await postCross(crossData);
                alert("Cross added successfully!");
            }
            setNewCross({ judgeNumber: '', dogNumbers: '', day: '', crossTime: '' });
            setEditMode(false);
            setEditCrossId(null);
            if (selectedDay) {
                loadCrossesForDay(selectedDay);
            }
        } catch (error) {
            console.error("Error saving cross:", error);
        }
    };

    const handleEdit = (cross) => {
        setEditMode(true);
        setEditCrossId(cross.id);
        setNewCross({
            judgeNumber: cross.judge.number,
            dogNumbers: cross.dogs.map(dog => dog.number).join(', '),
            day: cross.day,
            crossTime: `${String(Math.floor(cross.crossTime / 60)).padStart(2, '0')}:${String(cross.crossTime % 60).padStart(2, '0')}`
        });
    };

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this cross?")) {
            try {
                await deleteCross(id);
                alert("Cross deleted successfully!");
                if (selectedDay) {
                    loadCrossesForDay(selectedDay);
                }
            } catch (error) {
                console.error("Error deleting cross:", error);
            }
        }
    };

    const handleFilterByJudge = async (number) => {
        try {
            const response = await getCrossByJudge(number);
            setCrosses(response.data);
        } catch (error) {
            console.error("Error filtering crosses by judge:", error);
        }
    };

    const handleFilterByDog = async (number) => {
        try {
            const response = await getCrossByDog(number);
            setCrosses(response.data);
        } catch (error) {
            console.error("Error filtering crosses by dog:", error);
        }
    };

    return (
        <Container fluid className="py-4" style={{ backgroundColor: '#f8f9fa', borderRadius: '10px', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)', maxWidth: '1000px' }}>
            <h2 className="text-center mb-4">Cross Management</h2>

            {/* Cross List */}
            <div className="p-3" style={{ overflowY: 'auto', maxHeight: '50vh' }}>
                <Table striped bordered hover responsive>
                    <thead>
                        <tr>
                            <th>Judge Number</th>
                            <th>Dogs</th>
                            <th>Day</th>
                            <th>Cross Time (HH:MM)</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {crosses.map((cross) => (
                            <tr key={cross.id}>
                                <td>{cross.judge.number}</td>
                                <td>{cross.dogs.map(dog => dog.number).join(', ')}</td>
                                <td>{cross.day}</td>
                                <td>{`${String(Math.floor(cross.crossTime / 60)).padStart(2, '0')}:${String(cross.crossTime % 60).padStart(2, '0')}`}</td>
                                <td>
                                    <OverlayTrigger
                                        placement="top"
                                        overlay={<Tooltip>Edit Cross</Tooltip>}
                                    >
                                        <Button variant="warning" size="sm" onClick={() => handleEdit(cross)} className="me-2">
                                            Edit
                                        </Button>
                                    </OverlayTrigger>
                                    <OverlayTrigger
                                        placement="top"
                                        overlay={<Tooltip>Delete Cross</Tooltip>}
                                    >
                                        <Button variant="danger" size="sm" onClick={() => handleDelete(cross.id)}>
                                            Delete
                                        </Button>
                                    </OverlayTrigger>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </div>

            {/* Add/Edit Cross Form */}
            <div className="p-3 mt-4" style={{ backgroundColor: '#e9ecef', borderRadius: '10px' }}>
                <h4 className="text-center mb-3">{editMode ? "Edit Cross" : "Add New Cross"}</h4>
                <Form onSubmit={handleSubmit}>
                    <Row className="align-items-center mb-3">
                        <Col xs={4} md={2}>
                            <Form.Label>Judge Number:</Form.Label>
                        </Col>
                        <Col xs={8} md={10}>
                            <Form.Control
                                type="text"
                                name="judgeNumber"
                                value={newCross.judgeNumber}
                                onChange={handleInputChange}
                                placeholder="Enter Judge Number"
                                required
                            />
                        </Col>
                    </Row>
                    <Row className="align-items-center mb-3">
                        <Col xs={4} md={2}>
                            <Form.Label>Dog Numbers:</Form.Label>
                        </Col>
                        <Col xs={8} md={10}>
                            <Form.Control
                                type="text"
                                name="dogNumbers"
                                value={newCross.dogNumbers}
                                onChange={handleInputChange}
                                placeholder="Enter Dog Numbers (comma-separated)"
                                required
                            />
                        </Col>
                    </Row>
                    <Row className="align-items-center mb-3">
                        <Col xs={4} md={2}>
                            <Form.Label>Day:</Form.Label>
                        </Col>
                        <Col xs={8} md={10}>
                            <OverlayTrigger
                                placement='top'
                                overlay={<Tooltip>Days are 0 indexed. <br/> 1st Day = Day 0</Tooltip>}
                            >
                                <Form.Control
                                    type="number"
                                    name="day"
                                    min={0}
                                    max={3}
                                    value={newCross.day}
                                    onChange={handleInputChange}
                                    placeholder="Enter Day"
                                    required
                                />
                            </OverlayTrigger>
                        </Col>
                    </Row>
                    <Row className="align-items-center mb-3">
                        <Col xs={4} md={2}>
                            <Form.Label>Cross Time:</Form.Label>
                        </Col>
                        <Col xs={8} md={10}>
                            <Form.Control
                                type="time"
                                name="crossTime"
                                value={newCross.crossTime}
                                onChange={handleInputChange}
                                required
                            />
                        </Col>
                    </Row>
                    <Button type="submit" variant="primary" className="w-100">
                        {editMode ? "Update Cross" : "Add Cross"}
                    </Button>
                </Form>
            </div>

            {/* Filter Section */}
            <div className="d-flex justify-content-around mt-4">
                <Form.Control
                    type="number"
                    placeholder="Filter by Day"
                    min={0}
                    max={3}
                    onChange={(e) => setSelectedDay(e.target.value)}
                    style={{ maxWidth: '200px' }}
                />
                <Form.Control
                    type="text"
                    placeholder="Filter by Judge Number"
                    onChange={(e) => handleFilterByJudge(e.target.value)}
                    style={{ maxWidth: '200px' }}
                />
                <Form.Control
                    type="text"
                    placeholder="Filter by Dog Number"
                    onChange={(e) => handleFilterByDog(e.target.value)}
                    style={{ maxWidth: '200px' }}
                />
            </div>
        </Container>
    );
};

export default CrossManagement;
