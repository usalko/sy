from django.test import TestCase
from faker import Faker

from app_moods.models import GeometryShape

fake = Faker()


class GeometryShapeTest(TestCase):

    @classmethod
    def setUpTestData(cls):
        """Quickly set up data for the whole TestCase"""
        cls.first_name = fake.first_name()
        cls.user_last = fake.last_name()

    def test_create_models(self):
        """Creating a Geometry object"""
        # In test methods, use the variables created above
        triangle_shape = GeometryShape.objects.create(
            mnemonic='triangle',
        )
        #another_model = AnotherModel.objects.get(my_model=test_object)
        self.assertEqual(triangle_shape.mnemonic, 'triangle')
