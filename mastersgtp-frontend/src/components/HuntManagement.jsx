import React, { useEffect, useState } from 'react';
import { Accordion, Button, Col, Container, Form, OverlayTrigger, Row, Tooltip } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { getHunt } from '../service/HuntService';

const HuntManagement = () => {
    const [hunt, setHunt] = useState({
        name: '',
        date: '',
        timeInterval: '',
    });
    const [startTimes, setStartTimes] = useState(['', '', '', '']);

    useEffect(() => {
        getHunt()
            .then(ret => {
                if (ret.data) {
                    const formattedTimes = ret.data.startTimes.map(total => {
                        const hours = Math.floor(total / 60);
                        const minutes = total % 60;
                        return total === 0 ? '' : `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`;
                    });
                    setStartTimes(formattedTimes);
                    setHunt(ret.data);
                }
            })
            .catch(err => console.log(err));
    }, []);

    const navigate = useNavigate();

    return (
        <Container className="py-4" fluid style={{ backgroundColor: '#f8f9fa', borderRadius: '10px', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)', maxWidth: '800px' }}>
            <h2 className="text-center mb-4">Hunt Management</h2>

            {/* Accordion for Collapsible Sections */}
            <Accordion defaultActiveKey="0">
                <Accordion.Item eventKey="0">
                    <Accordion.Header>Hunt Details</Accordion.Header>
                    <Accordion.Body style={{ borderRadius: '8px' }}>
                        <Form>
                            <Row className="align-items-center mb-3">
                                <Col xs={4} md={3}>
                                    <Form.Label className="fw-bold d-flex">Title:</Form.Label>
                                </Col>
                                <Col xs={8} md={9}>
                                    <Form.Control readOnly value={hunt.name} placeholder="Hunt Title" />
                                </Col>
                            </Row>
                            <Row className="align-items-center mb-3">
                                <Col xs={4} md={3}>
                                    <Form.Label className="fw-bold d-flex">Dates Held:</Form.Label>
                                </Col>
                                <Col xs={8} md={9}>
                                    <Form.Control readOnly value={hunt.date} placeholder="Dates" />
                                </Col>
                            </Row>
                            <Row className="align-items-center">
                                <Col xs={4} md={3}>
                                    <Form.Label className="fw-bold d-flex">Time Interval:</Form.Label>
                                </Col>
                                <Col xs={8} md={9}>
                                    <Form.Control readOnly value={hunt.timeInterval} placeholder="Time Interval" />
                                </Col>
                            </Row>
                        </Form>
                    </Accordion.Body>
                </Accordion.Item>

                <Accordion.Item eventKey="1">
                    <Accordion.Header>Start Times</Accordion.Header>
                    <Accordion.Body style={{ borderRadius: '8px' }}>
                        <Row className="text-center flex-column flex-md-row">
                            {['Day Zero', 'Day One', 'Day Two', 'Day Three'].map((day, index) => (
                                <Col key={index} className="mb-3 mb-md-0 d-flex flex-column align-items-center">
                                    <h6 className="">{day}</h6>
                                    <Form.Control readOnly type="time" value={startTimes[index]} style={{ textAlign: 'center' }} />
                                </Col>
                            ))}
                        </Row>
                    </Accordion.Body>
                </Accordion.Item>
            </Accordion>

            {/* Action Buttons */}
            <div className="d-flex flex-wrap justify-content-center mt-4">
                <OverlayTrigger placement="top" overlay={<Tooltip>Create a new hunt</Tooltip>}>
                    <Button className="m-2" variant="primary" onClick={() => navigate("/create-hunt")} style={{ minWidth: '150px' }}>New Hunt</Button>
                </OverlayTrigger>
                <OverlayTrigger placement="top" overlay={<Tooltip>Edit hunt information</Tooltip>}>
                    <Button className="m-2" variant="warning" onClick={() => navigate("/edit-hunt")} style={{ minWidth: '150px' }}>Edit Hunt Info</Button>
                </OverlayTrigger>
                <OverlayTrigger placement="top" overlay={<Tooltip>Set start times for each day</Tooltip>}>
                    <Button className="m-2" variant="secondary" onClick={() => navigate("/start-time")} style={{ minWidth: '150px' }}>Set Start Times</Button>
                </OverlayTrigger>
            </div>
        </Container>
    );
};

export default HuntManagement;
