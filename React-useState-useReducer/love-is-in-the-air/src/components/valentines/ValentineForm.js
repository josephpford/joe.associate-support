import { useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Image from 'react-bootstrap/Image'
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

function ValentineForm({ messages, setMessages, makeId }) {

    const { valentineId } = useParams();

    const { 
        register, 
        handleSubmit,
        setValue, 
        reset,
        formState: { errors } 
    } = useForm();

    const navigate = useNavigate();

    useEffect(() => {
        reset({ 
            valentineMessage: '',
            valentineSource: '',
            valentineCategory: ''
        });
    }, [window.location.pathname]);

    useEffect(() => {
        if (valentineId) {
            fetch("http://localhost:4000/valentines/" + valentineId)
            .then(response => {
                if (response.status == 404) {
                    setMessages([...messages, { id: makeId(), type: "failure", text: response.statusText }]);
                    navigate("/valentines");
                }
                if (response.status == 200) {
                    return response.json();
                }
            })
            .then(valentine => {
                setValue("valentineMessage", valentine.valentineMessage);
                setValue("valentineSource", valentine.valentineSource);
                setValue("valentineCategory", valentine.valentineCategory);
            })
            .catch(error => setMessages([...messages, { id: makeId(), type: "failure", text: error.message }]));
        }
    }, []);

    const onSubmit = (valentineData) => {
        let valentineDataCopy = {...valentineData};
        if (valentineId) {
            valentineDataCopy["id"] = valentineId;
            fetch("http://localhost:4000/valentines/" + valentineId, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(valentineDataCopy)
            })
            .then(response => {
                if (response.status == 200) {
                    setMessages([...messages, { id: makeId(), type: "success", text: "Valentine was successfully edited." }])
                }
            })
            .then(() => navigate("/valentines"))
            .catch(error => setMessages([...messages, { id: makeId(), type: "failure", text: error.message }]));
        } else {
            fetch("http://localhost:4000/valentines", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(valentineDataCopy)
            })
            .then(response => {
                if (response.status == 200) {
                    setMessages([...messages, { id: makeId(), type: "success", text: "Valentine was successfully added." }])
                }
            })
            .then(() => navigate("/valentines"))
            .catch(error => setMessages([...messages, { id: makeId(), type: "failure", text: error.message }]));
        };
    };

    return (
        <Row className="mt-4">
            <Col sm={{ span: 4 }}>
            <Image fluid className="mt-1 mb-4" src={process.env.PUBLIC_URL + "/images/bubble-gum-woman-with-a-big-magnet-attracts-likes.png"} alt="A woman uses a magnet to attract small pink hearts towards her" /></Col>
            <Col sm={{ span: 6 }}>
                <h2>Add Valentine</h2>
                <Form id="valentine-form" onSubmit={handleSubmit(onSubmit)}>
                    <Form.Group>
                        <Form.Label className="mt-3" htmlFor="valentine-message">Message</Form.Label>
                        <Form.Control
                            as="textarea"
                            id="valentine-message" 
                            {...register("valentineMessage", { required: "Must have a message." })}
                        />
                        <Form.Text className="form-error-message">{errors.valentineMessage?.message}</Form.Text>
                    </Form.Group>

                    <Form.Group>
                        <Form.Label className="mt-3" htmlFor="valentine-message">Source</Form.Label>
                        <Form.Control
                            type="text"
                            id="valentine-source" 
                            autoComplete="off" 
                            {...register("valentineSource", { required: "Must have a source." })}
                        />
                        <Form.Text className="form-error-message">{errors.valentineSource?.message}</Form.Text>
                    </Form.Group>

                    <Form.Group>
                        <Form.Label className="mt-3" htmlFor="valentine-category">Category</Form.Label>
                        <Form.Select 
                            id="valentine-category" 
                            aria-label="Select a category..." 
                            {...register("valentineCategory", { required: "Must have a category." })
                        }>
                            <option value="" disabled>Select a category...</option>
                            <option value="Funny">Funny</option>
                            <option value="Sentimental">Sentimental</option>
                            <option value="Cynical">Cynical</option>
                        </Form.Select>
                        <Form.Text className="form-error-message">{errors.valentineCategory?.message}</Form.Text>
                    </Form.Group>

                    <Button variant="primary" className="mt-3" type="submit">{valentineId ? "Edit" : "Add"}</Button>
                    <Button variant="secondary" className="mt-3 ms-2" type="button" onClick={() => navigate("/valentines")}>Cancel</Button>
                </Form>
            </Col>
        </Row>
        
    );
};

export default ValentineForm;