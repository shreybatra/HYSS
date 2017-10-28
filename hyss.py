from flask import Flask


from flask import jsonify, request
from geopy.distance import vincenty
import time

from flask import make_response



app = Flask(__name__)



min_dist = 1


client_data = [{'id':1,
				'name':"shrey",
				'password':"123"
}]

help_stations=[
{
	'id':1,
	'type':'Fire_Station',
	'username':'jay',
	'password':'xyz',
	'latitude':28.5678,
	'longitude':77.3258,
	'time':time.time()*1000
}
]

sos_data = [

{
	'id':1,
	'type':'Fire',
	'username':'username',
	'password':'password',
	'latitude':28.5864,
	'longitude':77.3414,
	'time':time.time()*1000
}

]



@app.route('/')
def hello():
	return 'hello duniyaaa'




@app.route('/help_station_add',methods=['POST'])
def help_station_add():
	iid = help_stations[-1]['id']+1
	type1 = request.json['type']
	username1 = request.json['username']
	password1 = request.json['password']
	latitude1 = request.json['latitude']
	longitude1 = request.json['longitude']
	timee = time.time()*1000

	aaa = {
		'id':iid,
		'type':type1,
		'username':username1,
		'password':password1,
		'latitude':latitude1,
		'longitude':longitude1,
		'time':timee
		}

	help_stations.append(aaa)

	f = open("help_stations.txt",'w')
	f.write(help_stations)
	f.close()

	return jsonify("help station added")





@app.route('/client_add',methods=['POST'])
def client_add():
	iid = client_data[-1]['id']+1
	username = request.json['username']
	password = request.json['password']
	aaa = {
		'id':iid,
		'username':username,
		'password':password
		}
	client_data.append(aaa)
	f = open("clients_data.txt",'w')
	f.write(client_data)
	f.close()
	return jsonify("client added")




@app.route('/sos_add',methods=['POST'])
def sos_add():
	iid = sos_data[-1]['id']+1
	type1 = request.json['type']
	username1 = request.json['username']
	password1 = request.json['password']
	latitude1 = request.json['latitude']
	longitude1 = request.json['longitude']
	timee = request.json['time']
	aaa = {
		'id':iid,
		'type':type1,
		'username':username1,
		'password':password1,
		'latitude':latitude1,
		'longitude':longitude1,
		'time':timee
		}
	sos_data.append(aaa)

	f = open("sos_data.txt",'w')
	abc = ''

	for word in sos_data:
		abc = abc + str(word['id']) + ' ' + str(word['type']) + ' ' + str(word['username']) + ' ' + str(word['password']) + ' ' + str(word['latitude']) + ' ' + str(word['longitude']) + ' ' + str(word['time']) + '\n'

	
	f.write(abc)
	f.close()

	return jsonify("SOS Added")





@app.route('/get_all_sos', methods=['GET'])
def get_all_sos():
	
	f = open('sos_data.txt','r')

	lines = f.readlines()

	abc = []

	for line in lines:
		sos = {
		'id':line.split()[0],
		'type':line.split()[1],
		'username':line.split()[2],
		'password':line.split()[3],
		'latitude':line.split()[4],
		'longitude':line.split()[5],
		'time':line.split()[6]
		}
		abc.append(sos)

	return jsonify({'sos data':abc})



@app.route('/get_all_clients', methods=['POST'])
def get_all_clients():
	return jsonify(client_data)


@app.route('/get_all_clients', methods=['POST'])
def get_all_help_stations():
	return jsonify(help_stations)



@app.route('/get_sos',methods=['POST'])
def get_sos():
	iid = request.json.get('id',None)
	type = request.json.get('type',None)
	old_time = request.json.get('time',0)

	help_location = (help_stations[0]['latitude'],help_stations[0]['longitude'])

	#return jsonify(help_stations)

	for help_station in help_stations:
		if help_station['id'] == iid:
			help_station['time'] = time.time()
			help_location = (help_station['latitude'],help_station['longitude'])

	#algo
	#return jsonify(help_location)
	new_sos = []
	

	for sos in sos_data:
		
		if sos['time'] >= old_time:
			sos_location = (sos['latitude'],sos['longitude'])
			if vincenty(sos_location,help_location).miles < min_dist:
				new_sos.append(sos)

	return jsonify(new_sos)



if __name__ == '__main__':
	app.run(debug=True)

