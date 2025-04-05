import pytest
from app import app

@pytest.fixture
def client():
    with app.test_client() as client:
        yield client

def test_homepage(client):
    """Test the homepage to ensure the message is rendered correctly."""
    response = client.get('/')
    assert response.status_code == 200
    assert b"Hello I am Adham" in response.data


