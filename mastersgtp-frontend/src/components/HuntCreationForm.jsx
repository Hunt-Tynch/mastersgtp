import React, { useState } from 'react';
import { Accordion, Button, Col, Container, Form, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { postHunt } from '../service/HuntService';

const HuntCreationForm = () => {
    const [title, setTitle] = useState('');
    const [datesHeld, setDatesHeld] = useState('');
    const [timeInterval, setTimeInterval] = useState('');
    const [startTimes, setStartTimes] = useState({
        dayZero: '00:00',
        dayOne: '00:00',
        dayTwo: '00:00',
        dayThree: '00:00',
    });
    const navigate = useNavigate();

    function timeToMinutes(time) {
        const [hours, minutes] = time.split(':').map(Number);
        return hours * 60 + minutes;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        const huntData = {
            name: title,
            date: datesHeld,
            timeInterval,
            startTimes: [
                timeToMinutes(startTimes.dayZero),
                timeToMinutes(startTimes.dayOne),
                timeToMinutes(startTimes.dayTwo),
                timeToMinutes(startTimes.dayThree),
            ]
        };
        try {
            await postHunt(huntData);
            alert('Hunt created successfully!');
            setTitle('');
            setDatesHeld('');
            setTimeInterval('');
            setStartTimes({ dayZero: '00:00', dayOne: '00:00', dayTwo: '00:00', dayThree: '00:00' });
            navigate('/');
        } catch (error) {
            console.error('Error creating hunt:', error);
            alert('Failed to create hunt. Please try again.');
        }
    };

    return (
        <Container fluid className="p-4" style={{ backgroundColor: '#f8f9fa', maxWidth: '800px', borderRadius: '10px', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.5)' }}>
            <h2 className="text-center mb-4" style={{ fontWeight: 'bold' }}>Create New Hunt</h2>
            
            <div className="p-4" style={{ backgroundColor: '#e9ecef', borderRadius: '8px' }}>
                <Form onSubmit={handleSubmit}>
                    {/* Title Input */}
                    <Form.Group as={Row} className="mb-3" controlId="huntTitle">
                        <Form.Label column sm="3" className="fw-bold d-flex">Title:</Form.Label>
                        <Col sm="9">
                            <Form.Control
                                type="text"
                                placeholder="Hunt Title"
                                value={title}
                                onChange={(e) => setTitle(e.target.value)}
                                style={{ fontSize: '1.1rem' }}
                            />
                        </Col>
                    </Form.Group>

                    {/* Dates Held Input */}
                    <Form.Group as={Row} className="mb-3" controlId="datesHeld">
                        <Form.Label column sm="3" className="fw-bold d-flex">Dates Held:</Form.Label>
                        <Col sm="9">
                            <Form.Control
                                type="text"
                                placeholder="Dates"
                                value={datesHeld}
                                onChange={(e) => setDatesHeld(e.target.value)}
                                style={{ fontSize: '1.1rem' }}
                            />
                        </Col>
                    </Form.Group>

                    {/* Time Interval Input */}
                    <Form.Group as={Row} className="mb-3" controlId="timeInterval">
                        <Form.Label column sm="3" className="fw-bold d-flex">Time Interval:</Form.Label>
                        <Col sm="9">
                            <Form.Control
                                type="number"
                                placeholder="Time Interval"
                                value={timeInterval}
                                onChange={(e) => setTimeInterval(e.target.value)}
                                style={{ fontSize: '1.1rem' }}
                            />
                        </Col>
                    </Form.Group>

                    {/* Start Times Section */}
                    <Accordion className="mt-4">
                        <Accordion.Item eventKey="0">
                            <Accordion.Header>Start Times</Accordion.Header>
                            <Accordion.Body className="p-3" style={{ borderRadius: '8px' }}>
                                <Row className="text-center">
                                    {['dayZero', 'dayOne', 'dayTwo', 'dayThree'].map((day, index) => (
                                        <Col key={index} xs={6} md={3} className="mb-3">
                                            <h6 className="">{`Day ${index}`}</h6>
                                            <Form.Control
                                                type="time"
                                                value={startTimes[day]}
                                                onChange={(e) =>
                                                    setStartTimes({ ...startTimes, [day]: e.target.value })
                                                }
                                                style={{ textAlign: 'center' }}
                                            />
                                        </Col>
                                    ))}
                                </Row>
                            </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>

                    {/* Submit Button */}
                    <Button type="submit" variant="primary" className="mt-4 w-100" style={{ fontSize: '1.25rem' }}>
                        Create Hunt
                    </Button>
                </Form>
            </div>
        </Container>
    );
};

export default HuntCreationForm;
