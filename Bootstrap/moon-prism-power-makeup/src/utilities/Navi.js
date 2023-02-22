import { LinkContainer } from 'react-router-bootstrap'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

function Navi() {

    return (
        <Navbar bg="primary" variant="dark" expand="md" className="mb-4">
            <Container fluid>
                <LinkContainer to="/">
                    <Navbar.Brand>
                        🌙 Moon Prism Power Makeup! 🌟
                    </Navbar.Brand>
                </LinkContainer>
                <Nav className="me-auto mb-2 mb-md-0">
                    <LinkContainer to="/" className={({ isActive }) => isActive ? "nav-link active" : "nav-link"}>
                        <Nav.Link>
                            Home
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