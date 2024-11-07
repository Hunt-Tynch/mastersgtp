import React, { useEffect, useState } from 'react';
import { Button, Col, Container, Form, OverlayTrigger, Row, Tooltip } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { getHunt, putHunt } from '../service/HuntService';

const EditHuntInfo = () => {
    const [hunt, setHunt] = useState({ name: '', date: '', timeInterval: '' });
    const navigate = useNavigate();

    useEffect(() => {
        getHunt()
            .then(ret => { if (ret.data) setHunt(ret.data); })
            .catch(err => console.log(err));
    }, []);

    const handleChange = (e) => setHunt({ ...hunt, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await putHunt(hunt);
            alert("Hunt information updated successfully!");
            navigate('/');
        } catch (error) {
            console.error("Failed to update hunt information:", error);
            alert("Error updating hunt information.");
        }
    };

    return (
        <Container fluid className="py-4" style={{ backgroundColor: '#f8f9fa', maxWidth: '600px', borderRadius: '10px', boxShadow: '0px 0px 10px rgba(0,0,0,0.5)' }}>
            <h2 className="text-center mb-4">Edit Hunt Information</h2>
            <Form onSubmit={handleSubmit} style={{ backgroundColor: '#e9ecef', borderRadius: '10px', padding: '20px' }}>
                <Form.Group as={Row} className="mb-3" controlId="huntTitle">
                    <Form.Label column sm="4" className="d-flex fw-bold">Title:</Form.Label>
                    <Col sm="8">
                        <Form.Control type="text" name="name" value={hunt.name} onChange={handleChange} placeholder="Hunt Title" />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="datesHeld">
                    <Form.Label column sm="4" className="d-flex fw-bold">Dates Held:</Form.Label>
                    <Col sm="8">
                        <Form.Control type="text" name="date" value={hunt.date} onChange={handleChange} placeholder="Dates" />
                    </Col>
                </Form.Group>
                <Form.Group as={Row} className="mb-3" controlId="timeInterval">
                    <Form.Label column sm="4" className="d-flex fw-bold">Time Interval:</Form.Label>
                    <Col sm="8">
                        <Form.Control type="number" name="timeInterval" value={hunt.timeInterval} onChange={handleChange} placeholder="Time Interval (minutes)" />
                    </Col>
                </Form.Group>
                <OverlayTrigger placement="top" overlay={<Tooltip>Save changes to hunt information</Tooltip>}>
                    <Button type="submit" variant="primary" className="w-100">Save Changes</Button>
                </OverlayTrigger>
            </Form>
        </Container>
    );
};

export default EditHuntInfo;
