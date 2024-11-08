import React, { useEffect, useState } from 'react';
import { Accordion, Button, Col, Container, Form, Row, Table } from 'react-bootstrap';
import {
    addDogsBatch,
    deleteDog,
    editDog,
    getAllDogs,
    getDogByNumber,
    getDogsByOwner,
    getDogsByTotalAndStake
} from '../service/DogService';

const DogManagement = () => {
    const createEmptyDog = () => ({
        number: '',
        name: '',
        stake: '',
        regNumber: '',
        sire: '',
        dam: '',
        total: 0,
    });

    const [dogs, setDogs] = useState([]);
    const [newDogs, setNewDogs] = useState([createEmptyDog()]);
    const [ownerInfo, setOwnerInfo] = useState({
        owner: '',
        ownerTown: '',
        ownerState: ''
    });
    const [editMode, setEditMode] = useState(false);
    const [editDogNumber, setEditDogNumber] = useState(null);
    const [sireSuggestions, setSireSuggestions] = useState([]);
    const [damSuggestions, setDamSuggestions] = useState([]);

    useEffect(() => {
        loadAllDogs();
    }, []);

    useEffect(() => {
        updateSuggestions(newDogs);
    }, [newDogs]);

    const loadAllDogs = async () => {
        try {
            const response = await getAllDogs();
            setDogs(response.data);
        } catch (error) {
            console.error("Error loading dogs:", error);
        }
    };

    const addDogRow = () => setNewDogs([...newDogs, createEmptyDog()]);

    const handleOwnerInputChange = (e) => {
        const { name, value } = e.target;
        setOwnerInfo({ ...ownerInfo, [name]: value });
    };

    const handleDogInputChange = (index, e) => {
        const { name, value } = e.target;
        const updatedDogs = [...newDogs];
        updatedDogs[index] = { ...updatedDogs[index], [name]: value };
        setNewDogs(updatedDogs);
    };

    const updateSuggestions = (dogs) => {
        const sires = Array.from(new Set(dogs.map(dog => dog.sire).filter(sire => sire)));
        const dams = Array.from(new Set(dogs.map(dog => dog.dam).filter(dam => dam)));
        setSireSuggestions(sires);
        setDamSuggestions(dams);
    };

    const handleAddDogs = async (e) => {
        e.preventDefault();
        const dogsWithOwnerInfo = newDogs.map(dog => ({ ...dog, ...ownerInfo }));
        try {
            await addDogsBatch(dogsWithOwnerInfo);
            alert("Dogs added successfully!");
            setNewDogs([createEmptyDog()]);
            loadAllDogs();
        } catch (error) {
            console.error("Error adding dogs:", error);
        }
    };

    const handleEdit = (dog) => {
        setEditMode(true);
        setEditDogNumber(dog.number);
        setNewDogs([{ ...dog}]);
        console.log(newDogs)
    };

    const handleUpdateDog = async (e) => {
        e.preventDefault();
        if (editDogNumber !== null) {
            try {
                newDogs[0].escores = [0,0,0,0]
                newDogs[0].sdscores = [0,0,0,0]
                await editDog(newDogs[0]);
                alert("Dog updated successfully!");
                setEditMode(false);
                setEditDogNumber(null);
                setNewDogs([createEmptyDog()]);
                loadAllDogs();
            } catch (error) {
                console.error("Error updating dog:", error);
            }
        }
    };

    const handleDelete = async (number) => {
        if (window.confirm("Are you sure you want to delete this dog?")) {
            try {
                await deleteDog(number);
                alert("Dog deleted successfully!");
                loadAllDogs();
            } catch (error) {
                console.error("Error deleting dog:", error);
            }
        }
    };

    const handleRemoveDog = (index) => {
        setNewDogs(newDogs.filter((_, i) => i !== index));
    };

    const handleFilterChange = async (type, value) => {
        try {
            if (type === 'number') {
                const response = await getDogByNumber(value);
                setDogs(response.data ? [response.data] : []);
            } else if (type === 'owner') {
                const response = await getDogsByOwner(value);
                setDogs(response.data);
            } else if (type === 'stake') {
                const response = await getDogsByTotalAndStake(value);
                setDogs(response.data);
            } else {
                loadAllDogs();
            }
        } catch (error) {
            console.error("Error filtering dogs:", error);
        }
    };
    
    return (
        <Container className="py-4" fluid style={{ backgroundColor: '#f8f9fa', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)', borderRadius: '10px', maxWidth: '1000px' }}>
            <h2 className="text-center">Dog Management</h2>

            {/* Accordion for Collapsible Sections */}
            <Accordion defaultActiveKey="0">
                {/* Owner Information */}
                <Accordion.Item eventKey="0">
                    <Accordion.Header>Owner Information</Accordion.Header>
                    <Accordion.Body>
                        <Form>
                            <Row className="align-items-center mb-3">
                                <Col xs={4} md={2}>
                                    <Form.Label>Owner</Form.Label>
                                </Col>
                                <Col xs={8} md={10}>
                                    <Form.Control
                                        type="text"
                                        name="owner"
                                        value={ownerInfo.owner}
                                        onChange={handleOwnerInputChange}
                                        placeholder="Owner"
                                        required
                                    />
                                </Col>
                            </Row>
                            <Row className="align-items-center mb-3">
                                <Col xs={4} md={2}>
                                    <Form.Label>Town</Form.Label>
                                </Col>
                                <Col xs={8} md={10}>
                                    <Form.Control
                                        type="text"
                                        name="ownerTown"
                                        value={ownerInfo.ownerTown}
                                        onChange={handleOwnerInputChange}
                                        placeholder="Owner Town"
                                    />
                                </Col>
                            </Row>
                            <Row className="align-items-center mb-3">
                                <Col xs={4} md={2}>
                                    <Form.Label>State</Form.Label>
                                </Col>
                                <Col xs={8} md={10}>
                                    <Form.Control
                                        type="text"
                                        name="ownerState"
                                        value={ownerInfo.ownerState}
                                        onChange={handleOwnerInputChange}
                                        placeholder="Owner State"
                                    />
                                </Col>
                            </Row>
                        </Form>
                    </Accordion.Body>
                </Accordion.Item>

                {/* Dog Information */}
                <Accordion.Item eventKey="1">
                    <Accordion.Header>Dog Information</Accordion.Header>
                    <Accordion.Body>
                        <h3>{editMode ? 'Edit Dog':'Dog entries'}</h3>
                        <Form onSubmit={editMode ? handleUpdateDog : handleAddDogs}>
                            {newDogs.map((dog, index) => (
                                <Row key={index} className="align-items-center g-2 mb-2">
                                    <Col xs={1}>
                                        <Form.Control
                                            type="number"
                                            name="number"
                                            value={dog.number}
                                            onChange={(e) => handleDogInputChange(index, e)}
                                            placeholder="#"
                                            required
                                        />
                                    </Col>
                                    <Col xs={2}>
                                        <Form.Control
                                            type="text"
                                            name="name"
                                            value={dog.name}
                                            onChange={(e) => handleDogInputChange(index, e)}
                                            placeholder="Dog Name"
                                            required
                                        />
                                    </Col>
                                    <Col xs={2}>
                                        <Form.Control
                                            as="select"
                                            name="stake"
                                            value={dog.stake}
                                            onChange={(e) => handleDogInputChange(index, e)}
                                            required
                                        >
                                            <option value="">Select Stake</option>
                                            <option value="DERBY">DERBY</option>
                                            <option value="ALL_AGE">ALL_AGE</option>
                                        </Form.Control>
                                    </Col>
                                    <Col xs={2}>
                                        <Form.Control
                                            type="text"
                                            name="regNumber"
                                            value={dog.regNumber}
                                            onChange={(e) => handleDogInputChange(index, e)}
                                            placeholder="Reg Number"
                                        />
                                    </Col>
                                    <Col xs={2}>
                                        <Form.Control
                                            type="text"
                                            name="sire"
                                            list="sireSuggestions"
                                            value={dog.sire}
                                            onChange={(e) => handleDogInputChange(index, e)}
                                            placeholder="Sire"
                                        />
                                        <datalist id="sireSuggestions">
                                            {sireSuggestions.map((sire, i) => (
                                                <option key={i} value={sire} />
                                            ))}
                                        </datalist>
                                    </Col>
                                    <Col xs={2}>
                                        <Form.Control
                                            type="text"
                                            name="dam"
                                            list="damSuggestions"
                                            value={dog.dam}
                                            onChange={(e) => handleDogInputChange(index, e)}
                                            placeholder="Dam"
                                        />
                                        <datalist id="damSuggestions">
                                            {damSuggestions.map((dam, i) => (
                                                <option key={i} value={dam} />
                                            ))}
                                        </datalist>
                                    </Col>
                                    <Col xs={1}>
                                        <Button variant="danger" size="sm" onClick={() => handleRemoveDog(index)}>
                                            Remove
                                        </Button>
                                    </Col>
                                </Row>
                            ))}
                            <Button variant="success" disabled={editMode} onClick={addDogRow} className="w-100 mb-3">
                                Add Row
                            </Button>
                            <Button type="submit" variant="primary" className="w-100">
                                {editMode ? "Update Dog" : "Add All Dogs"}
                            </Button>
                        </Form>
                    </Accordion.Body>
                </Accordion.Item>
            </Accordion>

            {/* Dog List */}
            <div className="p-3 mt-4" style={{ background: '#f1f1f1', borderRadius: '10px', overflowY: 'auto', maxHeight: '30vh', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.2)' }}>
                <h5 className="text-center mb-3">Dog List</h5>
                <div className="d-flex justify-content-around mb-3">
                    <Button onClick={() => handleFilterChange('all', '')}>View All Dogs</Button>
                    <Button onClick={() => handleFilterChange('owner', ownerInfo.owner)}>View Dogs by Owner</Button>
                    <Button onClick={() => handleFilterChange('stake', 'DERBY')}>View DERBY Dogs</Button>
                    <Button onClick={() => handleFilterChange('stake', 'ALL_AGE')}>View ALL_AGE Dogs</Button>
                    <Form.Control
                        type="number"
                        placeholder="Search by Dog #"
                        onChange={(e) => handleFilterChange('number', e.target.value)}
                        style={{ maxWidth: '200px' }}
                    />
                </div>
                <Table striped bordered hover variant="light">
                    <thead>
                        <tr>
                            <th>Number</th>
                            <th>Name</th>
                            <th>Stake</th>
                            <th>Total</th>
                            <th>Scratched</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {dogs.map((dog) => (
                            <tr key={dog.number}>
                                <td>{dog.number}</td>
                                <td>{dog.name}</td>
                                <td>{dog.stake}</td>
                                <td>{dog.total}</td>
                                <td>{dog.scratched ? <p>&#x274c;</p> : ''}</td>
                                <td>
                                    <Button variant="warning" size="sm" onClick={() => handleEdit(dog)} className="me-2">
                                        Edit
                                    </Button>
                                    <Button variant="danger" size="sm" onClick={() => handleDelete(dog.number)}>
                                        Delete
                                    </Button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </div>
        </Container>
    );
};

export default DogManagement;
