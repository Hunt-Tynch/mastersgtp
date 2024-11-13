import React, { useEffect, useState } from 'react';
import { Button, Col, Container, Form, OverlayTrigger, Row, Table, Tooltip } from 'react-bootstrap';
import { deleteJudge, getJudges, postJudge, putJudge } from '../service/JudgeService';

const JudgeManagement = () => {
    const [judges, setJudges] = useState([]);
    const [newJudge, setNewJudge] = useState({ name: '', pin: '', number: '' });
    const [editMode, setEditMode] = useState(false);
    const [editJudgeNumber, setEditJudgeNumber] = useState(null);

    useEffect(() => {
        loadJudges();
    }, []);

    const loadJudges = async () => {
        try {
            const response = await getJudges();
            setJudges(response.data);
        } catch (error) {
            console.error("Error loading judges:", error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewJudge({ ...newJudge, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editMode) {
                await putJudge(editJudgeNumber, newJudge);
                alert("Judge updated successfully!");
            } else {
                await postJudge(newJudge);
                alert("Judge added successfully!");
            }
            setNewJudge({ name: '', pin: '', number: '' });
            setEditMode(false);
            setEditJudgeNumber(null);
            loadJudges();
        } catch (error) {
            console.error("Error saving judge:", error);
        }
    };

    const handleEdit = (judge) => {
        setEditMode(true);
        setEditJudgeNumber(judge.number);
        setNewJudge({ name: judge.name, pin: judge.pin, number: judge.number });
    };

    const handleDelete = async (number) => {
        if (window.confirm("Are you sure you want to delete this judge?")) {
            try {
                await deleteJudge(number);
                alert("Judge deleted successfully!");
                loadJudges();
            } catch (error) {
                console.error("Error deleting judge:", error);
            }
        }
    };

    return (
        <Container className="py-4" fluid style={{ backgroundColor: '#f8f9fa', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)', borderRadius: '10px', maxWidth: '800px' }}>
            <h2 className="text-center mb-4">Judge Management</h2>
            
            {/* Judge List */}
            <div className="p-3" style={{ overflowY: 'auto', maxHeight: '50vh' }}>
                <Table striped bordered hover responsive>
                    <thead>
                        <tr>
                            <th>Number</th>
                            <th>Name</th>
                            <th>PIN</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {judges.map((judge) => (
                            <tr key={judge.number}>
                                <td>{judge.number}</td>
                                <td>{judge.name}</td>
                                <td>{judge.pin}</td>
                                <td>
                                    <OverlayTrigger
                                        placement="top"
                                        overlay={<Tooltip id="tooltip-edit">Edit Judge</Tooltip>}
                                    >
                                        <Button
                                            variant="warning"
                                            size="sm"
                                            onClick={() => handleEdit(judge)}
                                            className="me-2"
                                        >
                                            Edit
                                        </Button>
                                    </OverlayTrigger>
                                    <OverlayTrigger
                                        placement="top"
                                        overlay={<Tooltip id="tooltip-delete">Delete Judge</Tooltip>}
                                    >
                                        <Button
                                            variant="danger"
                                            size="sm"
                                            onClick={() => handleDelete(judge.number)}
                                        >
                                            Delete
                                        </Button>
                                    </OverlayTrigger>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </div>

            {/* Add/Edit Judge Form */}
            <div className="p-3 mt-4" style={{ backgroundColor: '#e9ecef', borderRadius: '10px' }}>
                <h4 className="text-center mb-3">{editMode ? "Edit Judge" : "Add New Judge"}</h4>
                <Form onSubmit={handleSubmit}>
                    <Row className="align-items-center mb-3">
                        <Col xs={4} md={2}>
                            <Form.Label className="d-flex">Number:</Form.Label>
                        </Col>
                        <Col xs={8} md={10}>
                            <Form.Control
                                type="number"
                                name="number"
                                value={newJudge.number}
                                onChange={handleInputChange}
                                placeholder="Judge Number"
                                required
                                readOnly={editMode} 
                            />
                        </Col>
                    </Row>
                    <Row className="align-items-center mb-3">
                        <Col xs={4} md={2}>
                            <Form.Label className="d-flex">Name:</Form.Label>
                        </Col>
                        <Col xs={8} md={10}>
                            <Form.Control
                                type="text"
                                name="name"
                                value={newJudge.name}
                                onChange={handleInputChange}
                                placeholder="Judge Name"
                                required
                            />
                        </Col>
                    </Row>
                    <Row className="align-items-center mb-3">
                        <Col xs={4} md={2}>
                            <Form.Label className="d-flex">PIN:</Form.Label>
                        </Col>
                        <Col xs={8} md={10}>
                            <Form.Control
                                type="text"
                                name="pin"
                                value={newJudge.pin}
                                onChange={handleInputChange}
                                placeholder="Judge PIN"
                                required
                            />
                        </Col>
                    </Row>
                    <Button type="submit" variant="primary" className="w-100">
                        {editMode ? "Update Judge" : "Add Judge"}
                    </Button>
                </Form>
            </div>
        </Container>
    );
};

export default JudgeManagement;
