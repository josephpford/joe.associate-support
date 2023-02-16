import { LinkContainer } from 'react-router-bootstrap'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

function Navi() {

    return (
        <Navbar bg="primary" variant="light" expand="md" className="mb-5" fixed="top">
            <Container fluid>
                <LinkContainer to="/">
                    <Navbar.Brand>
                        💕 Love is in the Air!
                    </Navbar.Brand>
                </LinkContainer>
                <Nav className="me-auto mb-2 mb-md-0">
                    <LinkContainer to="/" className={({ isActive }) => isActive ? "nav-link active" : "nav-link"}>
                        <Nav.Link>
                            Home
                        </Nav.Link>
                    </LinkContainer>
                    <LinkContainer to="/valentines" className={({ isActive }) => isActive ? "nav-link active" : "nav-link"}>
                        <Nav.Link>
                            Valentines
                        </Nav.Link>
                    </LinkContainer>
                    <LinkContainer to="/valentines/add" className={({ isActive }) => isActive ? "nav-link active" : "nav-link"}>
                        <Nav.Link>
                            Add Valentine
                        </Nav.Link>
                    </LinkContainer>
                    <LinkContainer to="/about" className={({ isActive }) => isActive ? "nav-link active" : "nav-link"}>
                        <Nav.Link>
                            About
                        </Nav.Link>
                    </LinkContainer>
                </Nav>
            </Container>
        </Navbar>
    );
}

export default Navi;