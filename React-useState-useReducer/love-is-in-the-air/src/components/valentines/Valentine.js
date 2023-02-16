import { useParams, useNavigate } from 'react-router-dom';

import Card from 'react-bootstrap/Card';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Image from 'react-bootstrap/Image';
import Button from 'react-bootstrap/Button';

function Valentine({ valentine }) {

    const navigate = useNavigate();

    const { valentineId } = useParams();

    const colourCoder = () => {
        let baseClass = "valentine-card card-scalloped ";

        if (valentine.valentineCategory == "Funny") {
            return baseClass + "funny";
        } else if (valentine.valentineCategory == "Sentimental") {
            return baseClass + "sentimental";
        } else if (valentine.valentineCategory == "Cynical") {
            return baseClass + "cynical";
        } else {
            return baseClass;
        }
    }

    const renderButtons = () => {
        if (!valentineId) {
            return (
                <div className="valentine-buttons">
                    <Button size="sm" variant="secondary" onClick={() => navigate("/valentines/edit/" + valentine.id)}>Edit</Button>
                    <Button size="sm" variant="danger" className="ms-2" onClick={() => navigate("/valentines/delete/" + valentine.id)}>Delete</Button>
                </div>
            )
        }
    }

    return (
        <Card className={colourCoder()}>
            <Row>
                <Col sm={{ span: 2 }} >
                    <Image fluid className="ms-3 valentine-card-img" src={process.env.PUBLIC_URL + "/images/bubble-gum-pink-heart-with-highlight-1.png"} alt="Shiny pink heart at an angle" />
                </Col>
                <Col sm={{ span: 10 }}>
                    <Card.Body className="valentine-card-body">
                        <h5 className="m-0">{valentine.valentineMessage}</h5>
                        <p className="text-end opacity-50 fst-italic fs-6 mb-0">&mdash; {valentine.valentineSource}</p>
                        <p className="text-end valentine-category opacity-50">#{valentine.valentineCategory}</p>
                        {renderButtons()}
                    </Card.Body>
                </Col>
            </Row>
        </Card>
    );
}

export default Valentine;