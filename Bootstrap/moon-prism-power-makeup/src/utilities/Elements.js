import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Alert from 'react-bootstrap/Alert';
import Button from 'react-bootstrap/Button';

function Elements() {

    return (
        <>
            <h3>Buttons</h3>
            <hr />
            <Row className="mb-5">
                <Col>
                    <Button className="me-3" variant={"primary"}>
                        primary
                    </Button>
                    <Button className="me-3" variant={"secondary"}>
                        secondary
                    </Button>
                    <Button className="me-3" variant={"success"}>
                        success
                    </Button>
                    <Button className="me-3" variant={"danger"}>
                        danger
                    </Button>
                    <Button className="me-3" variant={"warning"}>
                        warning
                    </Button>
                    <Button className="me-3" variant={"info"}>
                        info
                    </Button>
                    <Button className="me-3" variant={"light"}>
                        light
                    </Button>
                    <Button variant={"dark"}>
                        dark
                    </Button>
                </Col>
            </Row>

            <h3>Alerts</h3>
            <hr />
            <Row className="mb-5">
                <Col>
                    <Alert variant={"primary"}>
                        This is a primary alert!
                    </Alert>
                    <Alert variant={"secondary"}>
                        This is a secondary alert!
                    </Alert>
                    <Alert variant={"success"}>
                        This is a success alert!
                    </Alert>
                    <Alert variant={"danger"}>
                        This is a danger alert!
                    </Alert>
                </Col>
                <Col>
                    <Alert variant={"warning"}>
                        This is a warning alert!
                    </Alert>
                    <Alert variant={"info"}>
                        This is a info alert!
                    </Alert>
                    <Alert variant={"light"}>
                        This is a light alert!
                    </Alert>
                    <Alert variant={"dark"}>
                        This is a dark alert!
                    </Alert>
                </Col>
            </Row>

            <h3>Typography</h3>
            <hr />
            <Row className="mb-5">
                <Col sm={{span: 5}}>
                    <h1>An H1 element!</h1>
                    <h2>An H2 element!</h2>
                    <h3>An H3 element!</h3>
                    <h4>An H4 element!</h4>
                    <h5>An H5 element!</h5>
                    <h6>An H6 element!</h6>
                </Col>
                <Col>
                    <p>A paragraph element!</p> 
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p> 
                    <p><i>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</i></p>
                    <p>Excepteur sint occaecat <b>cupidatat non proident</b> sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                </Col>
            </Row>
        </>
    );
}

export default Elements;